package it.pkg.application.domain.ports.secondary.repository.postgres.purchase_order;

import it.pkg.application.domain.model.domain.postgres.purchase_order.PurchaseOrderEntity;
import it.pkg.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PurchaseOrderRepository {

  PurchaseOrderEntity findByCode(String code);

  PurchaseOrderEntity findById(Integer id);

  Iterable<PurchaseOrderEntity> findAll();

  Page<PurchaseOrderEntity> findAll(Specification<PurchaseOrderEntity> spec, Pageable pageable);

  PurchaseOrderEntity save(PurchaseOrderEntity entity);

  void delete(PurchaseOrderEntity entity);

  default void deleteById(Integer id) {
    PurchaseOrderRepository service = SpringBeanUtil.getInstance().getBean(PurchaseOrderRepository.class);

    PurchaseOrderEntity entity = findById(id);
    if (entity != null) {
      service.delete(entity);
    } else {
      throw new EntityNotFoundException(
          String.format("Not exists %s: %s", PurchaseOrderEntity.class.getSimpleName(), id));
    }
  }

  void deleteAll();
}
