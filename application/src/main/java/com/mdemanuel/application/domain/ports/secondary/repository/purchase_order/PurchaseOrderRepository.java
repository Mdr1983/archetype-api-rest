package com.mdemanuel.application.domain.ports.secondary.repository.purchase_order;

import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderEntity;
import com.mdemanuel.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrderEntity, Integer>,
    JpaSpecificationExecutor<PurchaseOrderEntity> {

  @Query("SELECT p FROM PurchaseOrderEntity p JOIN FETCH p.purchaseOrderLines WHERE p.purchaseOrderCode = :code")
  @Cacheable(cacheNames = "purchase_order",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  PurchaseOrderEntity findByPurchaseOrderCode(String code);

  @Override
  @Query("SELECT p FROM PurchaseOrderEntity p JOIN FETCH p.purchaseOrderLines WHERE p.purchaseOrderId = :id")
  @Cacheable(cacheNames = "purchase_order",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  Optional<PurchaseOrderEntity> findById(Integer id);

  @Override
  @Query("SELECT p FROM PurchaseOrderEntity p JOIN FETCH p.purchaseOrderLines")
  @Cacheable(cacheNames = "purchase_order",
      key = "{ #root.methodName }",
      unless = "#result == null")
  Iterable<PurchaseOrderEntity> findAll();

  @Override
  Page<PurchaseOrderEntity> findAll(Specification<PurchaseOrderEntity> spec, Pageable pageable);

  @Caching(put = {
      @CachePut(cacheNames = "purchase_order",
          key = "{ 'findByCode', #result.purchaseOrderCode }",
          unless = "#result == null"),
      @CachePut(cacheNames = "purchase_order",
          key = "{ 'findById', #result.purchaseOrderId }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "purchase_order", allEntries = true)})
  <S extends PurchaseOrderEntity> S save(S entity);

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "purchase_order",
          key = "{ 'findByCode', #entity.purchaseOrderCode }"),
      @CacheEvict(cacheNames = "purchase_order",
          key = "{ 'findById', #entity.purchaseOrderId }"),
      @CacheEvict(cacheNames = "purchase_order", allEntries = true)
  })
  void delete(PurchaseOrderEntity entity);

  @Override
  default void deleteById(Integer id) {
    PurchaseOrderRepository service = SpringBeanUtil.getInstance().getBean(PurchaseOrderRepository.class);

    Optional<PurchaseOrderEntity> entity = findById(id);
    if (entity.isPresent()) {
      service.delete(entity.get());
    } else {
      throw new EntityNotFoundException(
          String.format("Not exists %s: %s", PurchaseOrderEntity.class.getSimpleName(), id));
    }
  }

  @Override
  @CacheEvict(cacheNames = "purchase_order", allEntries = true)
  void deleteAll();
}
