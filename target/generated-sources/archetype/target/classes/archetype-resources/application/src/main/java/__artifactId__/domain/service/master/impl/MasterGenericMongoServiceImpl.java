#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.master.impl;

import ${package}.${artifactId}.domain.model.domain.mongo.master.CategoryDocument;
import ${package}.${artifactId}.domain.model.domain.mongo.master.CategoryGenericDocument;
import ${package}.${artifactId}.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import ${package}.${artifactId}.domain.ports.primary.dto.request.GenericDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.ports.secondary.repository.RepositoryUtils;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import ${package}.${artifactId}.domain.service.cache.CacheService;
import ${package}.${artifactId}.domain.service.exceptions.ItemInUseException;
import ${package}.${artifactId}.domain.service.mapper.MasterGenericDtoMongoMapper;
import ${package}.${artifactId}.domain.service.master.MasterGenericMongoService;
import ${package}.${artifactId}.domain.service.util.DocumentMongoService;
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
    if (purchaseOrderGenericDocumentRepository.getCategoryRelated(code) > 0) {
      throw new ItemInUseException(CategoryDocument.class.getSimpleName(), "Code", code);
    }

    categoryGenericDocumentRepository.deleteById(categoryId);
  }
}
