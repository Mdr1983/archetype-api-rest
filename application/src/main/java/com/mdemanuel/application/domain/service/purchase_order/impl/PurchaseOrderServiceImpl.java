package com.mdemanuel.application.domain.service.purchase_order.impl;

import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderLineEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.RepositoryUtils;
import com.mdemanuel.application.domain.ports.secondary.repository.purchase_order.PurchaseOrderLineRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.purchase_order.PurchaseOrderRepository;
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

  @Override
  public List<PurchaseOrderDto> getAllPurchaseOrder() {
    return purchaseOrderDtoMapper.toPurchaseOrderDtoList(
        new ArrayList<>((Collection) purchaseOrderRepository.findAll()));
  }

  @Override
  public Page<PurchaseOrderDto> getAllPurchaseOrder(SearchCriteriaDto dto) {
    Page<PurchaseOrderEntity> result =  purchaseOrderRepository.findAll(entityService.getEntitySpecification(
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
    entityService.getEntityByCode(dto.getPurchaseOrderCode(), PurchaseOrderEntity.class, false, true);

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

    if (!code.equals(dto.getPurchaseOrderCode())) {
      entityService.getEntityByCode(dto.getPurchaseOrderCode(), PurchaseOrderEntity.class, false, true);
    }

    PurchaseOrderEntity newEntity = purchaseOrderRepository.save(purchaseOrderDtoMapper.toPurchaseOrderEntity(dto));
    newEntity.setPurchaseOrderId(entity.getPurchaseOrderId());

    for (PurchaseOrderLineEntity item : newEntity.getPurchaseOrderLines()) {
      item.setPurchaseOrder(newEntity);
    }

    purchaseOrderLineRepository.saveAll(newEntity.getPurchaseOrderLines());

    return dto;
  }

  @SneakyThrows
  @Override
  public void deletePurchaseOrder(String code) {
    purchaseOrderRepository.deleteById(
        entityService.getEntityByCode(code, PurchaseOrderEntity.class, true, false).getPurchaseOrderId());
  }
}
