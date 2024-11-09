#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.purchase_order.impl;

import ${package}.${artifactId}.domain.model.domain.postgres.purchase_order.PurchaseOrderEntity;
import ${package}.${artifactId}.domain.ports.primary.dto.request.PurchaseOrderDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.ports.secondary.repository.RepositoryUtils;
import ${package}.${artifactId}.domain.ports.secondary.repository.postgres.purchase_order.PurchaseOrderRepository;
import ${package}.${artifactId}.domain.service.cache.CacheService;
import ${package}.${artifactId}.domain.service.mapper.PurchaseOrderDtoMapper;
import ${package}.${artifactId}.domain.service.purchase_order.PurchaseOrderService;
import ${package}.${artifactId}.domain.service.util.EntityService;
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

    purchaseOrderRepository.deleteById(id);
  }
}
