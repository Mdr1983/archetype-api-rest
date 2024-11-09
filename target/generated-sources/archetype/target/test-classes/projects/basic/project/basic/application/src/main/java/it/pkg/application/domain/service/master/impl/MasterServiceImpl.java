package it.pkg.application.domain.service.master.impl;

import it.pkg.application.domain.model.domain.postgres.master.CategoryEntity;
import it.pkg.application.domain.ports.primary.dto.request.CategoryDto;
import it.pkg.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import it.pkg.application.domain.ports.secondary.repository.RepositoryUtils;
import it.pkg.application.domain.ports.secondary.repository.postgres.master.CategoryRepository;
import it.pkg.application.domain.service.cache.CacheService;
import it.pkg.application.domain.service.mapper.MasterDtoMapper;
import it.pkg.application.domain.service.master.MasterService;
import it.pkg.application.domain.service.util.EntityService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MasterServiceImpl implements MasterService {

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private MasterDtoMapper masterDtoMapper;
  @Autowired
  private EntityService entityService;
  @Autowired
  private CacheService cacheService;

  @Override
  public List<CategoryDto> getAllCategory() {
    return masterDtoMapper.toCategoryDtoList(new ArrayList<>((Collection) categoryRepository.findAll()));
  }

  @Override
  public Page<CategoryDto> getAllCategory(SearchCriteriaDto dto) {
    Page<CategoryEntity> result = categoryRepository.findAll(
        entityService.getEntitySpecification(CategoryEntity.class, dto), RepositoryUtils.getPageable(dto));

    return result.map(masterDtoMapper::toCategoryDto);
  }

  @SneakyThrows
  @Override
  public CategoryDto getCategory(String code) {
    return masterDtoMapper.toCategoryDto(entityService.getEntityByCode(code, CategoryEntity.class, true, false));
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public CategoryDto addCategory(CategoryDto dto) {
    entityService.getEntityByCode(dto.getData().getCode(), CategoryEntity.class, false, true);

    categoryRepository.save(masterDtoMapper.toCategoryEntity(dto));

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public CategoryDto updateCategory(CategoryDto dto, String code) {
    CategoryEntity entity = entityService.getEntityByCode(code, CategoryEntity.class, true, false);

    if (!code.equals(dto.getData().getCode())) {
      entityService.getEntityByCode(dto.getData().getCode(), CategoryEntity.class, false, true);
    }

    CategoryEntity newEntity = masterDtoMapper.toCategoryEntity(dto);
    newEntity.setId(entity.getId());

    categoryRepository.save(newEntity);

    if (!newEntity.getCode().equals(code)) {
      // Invalidar cache por si cambia el code, porque no es posible hacerlo en el repository
      String key = "findByCode," + code;

      cacheService.evict("category", key);
    }

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteCategory(String code) {
    categoryRepository.deleteById(
        entityService.getEntityByCode(code, CategoryEntity.class, true, false).getId());
  }
}
