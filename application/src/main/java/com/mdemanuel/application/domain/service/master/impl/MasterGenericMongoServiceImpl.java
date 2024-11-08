package com.mdemanuel.application.domain.service.master.impl;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryGenericDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.RepositoryUtils;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order.CustomPurchaseOrderGenericDocumentRepository;
import com.mdemanuel.application.domain.service.cache.CacheService;
import com.mdemanuel.application.domain.service.exceptions.ItemInUseException;
import com.mdemanuel.application.domain.service.mapper.MasterGenericDtoMongoMapper;
import com.mdemanuel.application.domain.service.master.MasterGenericMongoService;
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
public class MasterGenericMongoServiceImpl implements MasterGenericMongoService {

  @Autowired
  private GenericDocumentRepository<CategoryGenericDocument> categoryGenericDocumentRepository;
  @Autowired
  private GenericDocumentRepository<PurchaseOrderGenericDocument> purchaseOrderGenericDocumentRepository;
  @Autowired
  private CustomPurchaseOrderGenericDocumentRepository customPurchaseOrderGenericDocumentRepository;
  @Autowired
  private MasterGenericDtoMongoMapper masterGenericDtoMongoMapper;
  @Autowired
  private DocumentMongoService documentMongoService;
  @Autowired
  private CacheService cacheService;

  @Override
  public List<GenericDto> getAllCategory() {
    return masterGenericDtoMongoMapper.toGenericDtoList(
        new ArrayList<>((Collection) categoryGenericDocumentRepository.findAll()));
  }

  @Override
  public Page<GenericDto> getAllCategory(SearchCriteriaDto dto) {
    Page<CategoryGenericDocument> result = categoryGenericDocumentRepository.findAll(
        documentMongoService.getDocumentMongoSpecification(CategoryGenericDocument.class, dto),
        RepositoryUtils.getPageable(dto));

    return result.map(masterGenericDtoMongoMapper::toGenericDto);
  }

  @SneakyThrows
  @Override
  public GenericDto getCategory(String code) {
    return masterGenericDtoMongoMapper.toGenericDto(
        documentMongoService.getDocumentByCode(code, CategoryGenericDocument.class, true, false));
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public GenericDto addCategory(GenericDto dto) {
    documentMongoService.getDocumentByCode(dto.getData().get("code").toString(), CategoryGenericDocument.class, false,
        true);

    categoryGenericDocumentRepository.save(masterGenericDtoMongoMapper.toCategoryGenericDocument(dto));

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public GenericDto updateCategory(GenericDto dto, String code) {
    CategoryGenericDocument categoryGenericDocument = documentMongoService.getDocumentByCode(code,
        CategoryGenericDocument.class, true, false);

    if (!code.equals(dto.getData().get("code").toString())) {
      documentMongoService.getDocumentByCode(dto.getData().get("code").toString(),
          CategoryGenericDocument.class, false, true);
    }

    CategoryGenericDocument newDocument = masterGenericDtoMongoMapper.toCategoryGenericDocument(dto);
    newDocument.setId(categoryGenericDocument.getId());

    categoryGenericDocumentRepository.save(newDocument);

    if (!newDocument.getData().get("code").equals(code)) {
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
    String categoryId = documentMongoService.getDocumentByCode(code, CategoryGenericDocument.class, true, false)
        .getId();

    // Validación: Verificar si la categoría está siendo utilizada
    if (customPurchaseOrderGenericDocumentRepository.getCategoryRelated(code) > 0) {
      throw new ItemInUseException(CategoryDocument.class.getSimpleName(), "Code", code);
    }

    categoryGenericDocumentRepository.deleteById(categoryId);
  }
}
