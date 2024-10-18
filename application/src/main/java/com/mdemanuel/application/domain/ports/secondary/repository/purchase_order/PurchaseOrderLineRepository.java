package com.mdemanuel.application.domain.ports.secondary.repository.purchase_order;

import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderLineEntity;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderLineRepository extends CrudRepository<PurchaseOrderLineEntity, Integer> {

  @Override
  /*@Query(value = "SELECT * FROM purchase_order o "
      + "INNER JOIN purchase_order_line ol on ol.purchase_order_id = o.purchase_order_id "
      + "WHERE purchase_order_id = :id", nativeQuery = true)*/
  @Cacheable(cacheNames = "cache-purchase_order_line",
      key = "{ #root.methodName,'purchaseOrderLineEntity', #id }",
      unless = "#result == null")
  Optional<PurchaseOrderLineEntity> findById(Integer id);

  @Override
  /*@Query(value = "SELECT * FROM purchase_order o "
      + "INNER JOIN purchase_order_line ol on ol.purchase_order_id = o.purchase_order_id ", nativeQuery = true)*/
  @Cacheable(cacheNames = "cache-purchase_order_line",
      key = "{ #root.methodName,'purchaseOrderLineEntity'}",
      unless = "#result == null")
  Iterable<PurchaseOrderLineEntity> findAll();
}
