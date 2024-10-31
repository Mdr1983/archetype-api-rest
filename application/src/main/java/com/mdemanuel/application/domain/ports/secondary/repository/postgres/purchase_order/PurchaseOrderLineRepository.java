package com.mdemanuel.application.domain.ports.secondary.repository.postgres.purchase_order;

import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderLineEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderLineRepository extends CrudRepository<PurchaseOrderLineEntity, Integer> {

  void deleteByPurchaseOrderId(Integer id);
}
