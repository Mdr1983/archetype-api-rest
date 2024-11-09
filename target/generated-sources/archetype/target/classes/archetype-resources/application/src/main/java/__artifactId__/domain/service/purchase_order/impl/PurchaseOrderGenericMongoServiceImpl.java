#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.purchase_order.impl;

import ${package}.${artifactId}.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import ${package}.${artifactId}.domain.ports.primary.dto.request.GenericDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.ports.secondary.repository.RepositoryUtils;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import ${package}.${artifactId}.domain.service.cache.CacheService;
import ${package}.${artifactId}.domain.service.mapper.PurchaseOrderGenericDtoMongoMapper;
import ${package}.${artifactId}.domain.service.purchase_order.PurchaseOrderGenericMongoService;
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
public class PurchaseOrderGenericMongoServiceImpl implements PurchaseOrderGenericMongoService {

  @Autowired
  private GenericDocumentRepository<PurchaseOrderGenericDocument> purchaseOrderGenericDocumentRepository;
  @Autowired
  private PurchaseOrderGenericDtoMongoMapper purchaseOrderGenericDtoMongoMapper;
  @Autowired
  private DocumentMongoService documentMongoService;
  @Autowired
  private CacheService cacheService;

  @Override
  public List<GenericDto> getAllPurchaseOrder() {
    return purchaseOrderGenericDtoMongoMapper.toGenericDtoList(
        new ArrayList<>((Collection) purchaseOrderGenericDocumentRepository.findAll()));
  }

  @Override
  public Page<GenericDto> getAllPurchaseOrder(SearchCriteriaDto dto) {
    Page<PurchaseOrderGenericDocument> result = purchaseOrderGenericDocumentRepository.findAll(
        documentMongoService.getDocumentMongoSpecification(PurchaseOrderGenericDocument.class, dto),
        RepositoryUtils.getPageable(dto));

    return result.map(purchaseOrderGenericDtoMongoMapper::toGenericDto);
  }

  @SneakyThrows
  @Override
  public GenericDto getPurchaseOrder(String code) {
    return purchaseOrderGenericDtoMongoMapper.toGenericDto(
        documentMongoService.getDocumentByCode(code, PurchaseOrderGenericDocument.class, true, false));
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public GenericDto addPurchaseOrder(GenericDto dto) {
    documentMongoService.getDocumentByCode(dto.getData().get("code").toString(), PurchaseOrderGenericDocument.class,
        false, true);

    purchaseOrderGenericDocumentRepository.save(
        purchaseOrderGenericDtoMongoMapper.toPurchaseOrderDocument(dto));

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public GenericDto updatePurchaseOrder(GenericDto dto, String code) {
    PurchaseOrderGenericDocument document = documentMongoService.getDocumentByCode(code,
        PurchaseOrderGenericDocument.class, true, false);

    if (!code.equals(dto.getData().get("code"))) {
      documentMongoService.getDocumentByCode(dto.getData().get("code").toString(), PurchaseOrderGenericDocument.class,
          false, true);
    }

    PurchaseOrderGenericDocument newDocument = purchaseOrderGenericDtoMongoMapper.toPurchaseOrderDocument(dto);
    newDocument.setId(document.getId());

    purchaseOrderGenericDocumentRepository.save(newDocument);

    if (!newDocument.getData().get("code").equals(code)) {
      // Invalidar cache por si cambia el code, porque no es posible hacerlo en el repository
      String key = "findByCode," + code;

      cacheService.evict("purchaseOrderGenericDocument", key);
    }

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePurchaseOrder(String code) {
    purchaseOrderGenericDocumentRepository.deleteById(
        documentMongoService.getDocumentByCode(code, PurchaseOrderGenericDocument.class, true, false).getId());
  }
}
