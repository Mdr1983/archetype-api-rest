package com.mdemanuel.application.domain.service.purchase_order.impl;

import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderLineEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.RepositoryUtils;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.purchase_order.PurchaseOrderLineRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.purchase_order.PurchaseOrderRepository;
import com.mdemanuel.application.domain.service.cache.CacheService;
import com.mdemanuel.application.domain.service.mapper.PurchaseOrderDtoMapper;
import com.mdemanuel.application.domain.service.purchase_order.PurchaseOrderService;
import com.mdemanuel.application.domain.service.util.EntityService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

  @Autowired
  private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired
  private PurchaseOrderLineRepository purchaseOrderLineRepository;
  @Autowired
  private PurchaseOrderDtoMapper purchaseOrderDtoMapper;
  @Autowired
  private EntityService entityService;
  @Autowired
  private CacheService cacheService;

  @Override
  public List<PurchaseOrderDto> getAllPurchaseOrder() {
    return purchaseOrderDtoMapper.toPurchaseOrderDtoList(
        new ArrayList<>((Collection) purchaseOrderRepository.findAll()));
  }

  @Override
  public Page<PurchaseOrderDto> getAllPurchaseOrder(SearchCriteriaDto dto) {
    Page<PurchaseOrderEntity> result = purchaseOrderRepository.findAll(entityService.getEntitySpecification(
        PurchaseOrderEntity.class, dto), RepositoryUtils.getPageable(dto));

    return result.map(purchaseOrderDtoMapper::toPurchaseOrderDto);
  }

  @SneakyThrows
  @Override
  public PurchaseOrderDto getPurchaseOrder(String code) {
    return purchaseOrderDtoMapper.toPurchaseOrderDto(
        entityService.getEntityByCode(code, PurchaseOrderEntity.class, true, false));
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public PurchaseOrderDto addPurchaseOrder(PurchaseOrderDto dto) {
    entityService.getEntityByCode(dto.getData().getCode(), PurchaseOrderEntity.class, false, true);

    PurchaseOrderEntity entity = purchaseOrderRepository.save(purchaseOrderDtoMapper.toPurchaseOrderEntity(dto));

    for (PurchaseOrderLineEntity item : entity.getPurchaseOrderLines()) {
      item.setPurchaseOrder(entity);
    }

    purchaseOrderLineRepository.saveAll(entity.getPurchaseOrderLines());

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public PurchaseOrderDto updatePurchaseOrder(PurchaseOrderDto dto, String code) {
    PurchaseOrderEntity entity = entityService.getEntityByCode(code, PurchaseOrderEntity.class, true, false);

    if (!code.equals(dto.getData().getCode())) {
      entityService.getEntityByCode(dto.getData().getCode(), PurchaseOrderEntity.class, false, true);
    }

    PurchaseOrderEntity newEntity = purchaseOrderDtoMapper.toPurchaseOrderEntity(dto);
    newEntity.setId(entity.getId());

    for (PurchaseOrderLineEntity item : newEntity.getPurchaseOrderLines()) {
      item.setPurchaseOrder(newEntity);
    }

    purchaseOrderLineRepository.deleteByPurchaseOrderId(entity.getId());

    purchaseOrderLineRepository.saveAll(newEntity.getPurchaseOrderLines());

    newEntity = purchaseOrderRepository.save(newEntity);

    if (!newEntity.getCode().equals(code)) {
      // Invalidar cache por si cambia el code, porque no es posible hacerlo en el repository
      String key = "findByCode," + code;

      cacheService.evict("purchaseOrder", key);
    }

    return dto;
  }

  @SneakyThrows
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePurchaseOrder(String code) {
    Integer id = entityService.getEntityByCode(code, PurchaseOrderEntity.class, true, false).getId();

    purchaseOrderLineRepository.deleteByPurchaseOrderId(id);

    purchaseOrderRepository.deleteById(id);
  }
}
