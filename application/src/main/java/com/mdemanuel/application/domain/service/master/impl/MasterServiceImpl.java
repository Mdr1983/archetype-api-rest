package com.mdemanuel.application.domain.service.master.impl;

import com.mdemanuel.application.domain.model.domain.master.CategoryEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.master.CategoryRepository;
import com.mdemanuel.application.domain.service.mapper.MasterDtoMapper;
import com.mdemanuel.application.domain.service.master.MasterService;
import com.mdemanuel.application.domain.service.util.EntityService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Override
  public List<CategoryDto> getAllCategory() {
    return masterDtoMapper.toCategoryDtoList(new ArrayList<>((Collection) categoryRepository.findAll()));
  }

  @Override
  public List<CategoryDto> getAllCategory(SearchCriteriaDto dto) {
    return masterDtoMapper.toCategoryDtoList(
        new ArrayList<>(
            (Collection) categoryRepository.findAll(entityService.getEntitySpecification(CategoryEntity.class, dto))));
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
    entityService.getEntityByCode(dto.getCategoryCode(), CategoryEntity.class, false, true);

    categoryRepository.save(masterDtoMapper.toCategoryEntity(dto));

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public CategoryDto updateCategory(CategoryDto dto, String code) {
    CategoryEntity entity = entityService.getEntityByCode(code, CategoryEntity.class, true, false);

    if (!code.equals(dto.getCategoryCode())) {
      entityService.getEntityByCode(dto.getCategoryCode(), CategoryEntity.class, false, true);
    }

    CategoryEntity newEntity = masterDtoMapper.toCategoryEntity(dto);
    newEntity.setCategoryId(entity.getCategoryId());

    categoryRepository.save(newEntity);

    return dto;
  }

  @SneakyThrows
  @Override
  public void deleteCategory(String code) {
    categoryRepository.deleteById(
        entityService.getEntityByCode(code, CategoryEntity.class, true, false).getCategoryId());
  }
}
