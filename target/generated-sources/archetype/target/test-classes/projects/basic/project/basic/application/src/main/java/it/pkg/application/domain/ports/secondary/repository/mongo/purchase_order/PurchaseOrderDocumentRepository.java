package it.pkg.application.domain.ports.secondary.repository.mongo.purchase_order;

import it.pkg.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import it.pkg.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import it.pkg.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderDocumentRepository {

  PurchaseOrderDocument findByCode(String code);

  PurchaseOrderDocument findById(String id);

  List<PurchaseOrderDocument> findAll();

  Page<PurchaseOrderDocument> findAll(DocumentMongoSpecification documentMongoSpecification, Pageable pageable);

  <S extends PurchaseOrderDocument> S save(S entity);

  void delete(PurchaseOrderDocument entity);

  default void deleteById(String id) {
    PurchaseOrderDocumentRepository service = SpringBeanUtil.getInstance()
        .getBean(PurchaseOrderDocumentRepository.class);

    PurchaseOrderDocument entity = findById(id);
    if (entity != null) {
      service.delete(entity);
    } else {
      throw new EntityNotFoundException(
          String.format("Not exists %s: %s", PurchaseOrderDocument.class.getSimpleName(), id));
    }
  }

  void deleteAll();

  long getCategoryRelated(String categoryId);
}
