package it.pkg.application.domain.service.purchase_order.impl;

import it.pkg.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import it.pkg.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import it.pkg.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import it.pkg.application.domain.ports.secondary.repository.RepositoryUtils;
import it.pkg.application.domain.ports.secondary.repository.mongo.purchase_order.PurchaseOrderDocumentRepository;
import it.pkg.application.domain.service.cache.CacheService;
import it.pkg.application.domain.service.mapper.PurchaseOrderDtoMongoMapper;
import it.pkg.application.domain.service.purchase_order.PurchaseOrderMongoService;
import it.pkg.application.domain.service.util.DocumentMongoService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderMongoServiceImpl implements PurchaseOrderMongoService {

  @Autowired
  private PurchaseOrderDocumentRepository purchaseOrderDocumentRepository;
  @Autowired
  private PurchaseOrderDtoMongoMapper purchaseOrderDtoMongoMapper;
  @Autowired
  private DocumentMongoService documentMongoService;
  @Autowired
  private CacheService cacheService;

  @Override
  public List<PurchaseOrderDto> getAllPurchaseOrder() {
    return purchaseOrderDtoMongoMapper.toPurchaseOrderDtoList(
        new ArrayList<>((Collection) purchaseOrderDocumentRepository.findAll()));
  }

  @Override
  public Page<PurchaseOrderDto> getAllPurchaseOrder(SearchCriteriaDto dto) {
    Page<PurchaseOrderDocument> result = purchaseOrderDocumentRepository.findAll(
        documentMongoService.getDocumentMongoSpecification(PurchaseOrderDocument.class, dto),
        RepositoryUtils.getPageable(dto));

    return result.map(purchaseOrderDtoMongoMapper::toPurchaseOrderDto);
  }

  @SneakyThrows
  @Override
  public PurchaseOrderDto getPurchaseOrder(String code) {
    return purchaseOrderDtoMongoMapper.toPurchaseOrderDto(
        documentMongoService.getDocumentByCode(code, PurchaseOrderDocument.class, true, false));
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public PurchaseOrderDto addPurchaseOrder(PurchaseOrderDto dto) {
    documentMongoService.getDocumentByCode(dto.getData().getCode(), PurchaseOrderDocument.class, false, true);

    purchaseOrderDocumentRepository.save(purchaseOrderDtoMongoMapper.toPurchaseOrderDocument(dto));

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public PurchaseOrderDto updatePurchaseOrder(PurchaseOrderDto dto, String code) {
    PurchaseOrderDocument document = documentMongoService.getDocumentByCode(code, PurchaseOrderDocument.class, true,
        false);

    if (!code.equals(dto.getData().getCode())) {
      documentMongoService.getDocumentByCode(dto.getData().getCode(), PurchaseOrderDocument.class, false, true);
    }

    PurchaseOrderDocument newDocument = purchaseOrderDtoMongoMapper.toPurchaseOrderDocument(dto);
    newDocument.setId(document.getId());

    purchaseOrderDocumentRepository.save(newDocument);

    if (!newDocument.getCode().equals(code)) {
      // Invalidar cache por si cambia el code, porque no es posible hacerlo en el repository
      String key = "findByCode," + code;

      cacheService.evict("purchaseOrderDocument", key);
    }

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePurchaseOrder(String code) {
    purchaseOrderDocumentRepository.deleteById(
        documentMongoService.getDocumentByCode(code, PurchaseOrderDocument.class, true, false).getId());
  }
}
