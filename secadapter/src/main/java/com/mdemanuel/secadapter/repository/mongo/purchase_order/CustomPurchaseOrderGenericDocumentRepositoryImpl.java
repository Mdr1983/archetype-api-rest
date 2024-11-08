package com.mdemanuel.secadapter.repository.mongo.purchase_order;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import com.mdemanuel.application.domain.ports.secondary.repository.MongoCriteriaPageableQuery;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order.CustomPurchaseOrderGenericDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPurchaseOrderGenericDocumentRepositoryImpl implements CustomPurchaseOrderGenericDocumentRepository {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MongoCriteriaPageableQuery mongoCriteriaPageableQuery;

  @Override
  public Page<PurchaseOrderGenericDocument> findAll(DocumentMongoSpecification documentMongoSpecification,
      Pageable pageable) {
    return mongoCriteriaPageableQuery.find(documentMongoSpecification, pageable);
  }

  @Override
  public long getCategoryRelated(String categoryId) {
    Query query = new Query(Criteria.where("purchaseOrderLines.categoryId").is(categoryId));

    return mongoTemplate.count(query, PurchaseOrderDocument.class);
  }
}
