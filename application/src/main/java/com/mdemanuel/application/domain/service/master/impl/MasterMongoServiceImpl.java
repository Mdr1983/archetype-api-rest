package com.mdemanuel.application.domain.service.master.impl;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.RepositoryUtils;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.master.CategoryDocumentRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.master.CustomCategoryDocumentRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order.CustomPurchaseOrderDocumentRepository;
import com.mdemanuel.application.domain.service.cache.CacheService;
import com.mdemanuel.application.domain.service.exceptions.ItemInUseException;
import com.mdemanuel.application.domain.service.mapper.MasterDtoMongoMapper;
import com.mdemanuel.application.domain.service.master.MasterMongoService;
import com.mdemanuel.application.domain.service.util.DocumentMongoService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MasterMongoServiceImpl implements MasterMongoService {

  @Autowired
  private CategoryDocumentRepository categoryDocumentRepository;
  @Autowired
  private CustomCategoryDocumentRepository customCategoryDocumentRepository;
  @Autowired
  private CustomPurchaseOrderDocumentRepository customPurchaseOrderDocumentRepository;
  @Autowired
  private MasterDtoMongoMapper masterDtoMongoMapper;
  @Autowired
  private DocumentMongoService documentMongoService;
  @Autowired
  private CacheService cacheService;

  @Override
  public List<CategoryDto> getAllCategory() {
    return masterDtoMongoMapper.toCategoryDtoList(new ArrayList<>((Collection) categoryDocumentRepository.findAll()));
  }

  @Override
  public Page<CategoryDto> getAllCategory(SearchCriteriaDto dto) {
    Page<CategoryDocument> result = customCategoryDocumentRepository.findAll(
        documentMongoService.getDocumentMongoSpecification(CategoryDocument.class, dto),
        RepositoryUtils.getPageable(dto));

    return result.map(masterDtoMongoMapper::toCategoryDto);
  }

  @SneakyThrows
  @Override
  public CategoryDto getCategory(String code) {
    return masterDtoMongoMapper.toCategoryDto(
        documentMongoService.getDocumentByCode(code, CategoryDocument.class, true, false));
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public CategoryDto addCategory(CategoryDto dto) {
    documentMongoService.getDocumentByCode(dto.getData().getCode(), CategoryDocument.class, false, true);

    categoryDocumentRepository.save(masterDtoMongoMapper.toCategoryDocument(dto));

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public CategoryDto updateCategory(CategoryDto dto, String code) {
    CategoryDocument document = documentMongoService.getDocumentByCode(code, CategoryDocument.class, true, false);

    if (!code.equals(dto.getData().getCode())) {
      documentMongoService.getDocumentByCode(dto.getData().getCode(), CategoryDocument.class, false, true);
    }

    CategoryDocument newDocument = masterDtoMongoMapper.toCategoryDocument(dto);
    newDocument.setId(document.getId());

    categoryDocumentRepository.save(newDocument);

    if (!newDocument.getCode().equals(code)) {
      // Invalidar cache por si cambia el code, porque no es posible hacerlo en el repository
      String key = "findByCode," + code;

      cacheService.evict("categoryDocument", key);
    }

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteCategory(String code) {
    String categoryId = documentMongoService.getDocumentByCode(code, CategoryDocument.class, true, false).getId();

    // Validación: Verificar si la categoría está siendo utilizada
    if (customPurchaseOrderDocumentRepository.getCategoryRelated(categoryId) > 0) {
      throw new ItemInUseException(CategoryDocument.class.getSimpleName(), "Code", categoryId);
    }

    categoryDocumentRepository.deleteById(categoryId);
  }
}
