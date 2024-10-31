package com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomPurchaseOrderDocumentRepository<T> {

  Page<PurchaseOrderDocument> findAll(DocumentMongoSpecification<T> documentMongoSpecification, Pageable pageable);

  long getCategoryRelated(String categoryId);
}
