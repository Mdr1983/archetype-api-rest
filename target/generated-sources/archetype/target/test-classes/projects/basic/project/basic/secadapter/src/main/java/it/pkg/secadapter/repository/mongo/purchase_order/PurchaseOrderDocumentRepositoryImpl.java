package it.pkg.secadapter.repository.mongo.purchase_order;

import it.pkg.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import it.pkg.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import it.pkg.application.domain.ports.secondary.repository.MongoCriteriaPageableQuery;
import it.pkg.application.domain.ports.secondary.repository.mongo.purchase_order.PurchaseOrderDocumentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseOrderDocumentRepositoryImpl implements PurchaseOrderDocumentRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MongoCriteriaPageableQuery mongoCriteriaPageableQuery;

  @Cacheable(cacheNames = "purchaseOrderDocument",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  public PurchaseOrderDocument findByCode(String code) {
    org.springframework.data.mongodb.core.query.Query query = new Query();
    query.addCriteria(Criteria.where("data.code").is(code));

    return mongoTemplate.findOne(query, PurchaseOrderDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "purchaseOrderDocument",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  public PurchaseOrderDocument findById(String id) {
    return mongoTemplate.findById(id, PurchaseOrderDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "purchaseOrderDocument",
      key = "{ #root.methodName }",
      unless = "#result == null")
  public List<PurchaseOrderDocument> findAll() {
    return mongoTemplate.findAll(PurchaseOrderDocument.class);
  }

  @Override
  public Page<PurchaseOrderDocument> findAll(DocumentMongoSpecification documentMongoSpecification, Pageable pageable) {
    return mongoCriteriaPageableQuery.find(documentMongoSpecification, pageable);
  }


  @Caching(put = {
      @CachePut(cacheNames = "purchaseOrderDocument",
          key = "{ 'findByCode', #result.getCode() }",
          unless = "#result == null"),
      @CachePut(cacheNames = "purchaseOrderDocument",
          key = "{ 'findById', #result.getId() }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "purchaseOrderDocument", key = "{ 'findAll' }")})
  public <S extends PurchaseOrderDocument> S save(S entity) {
    return mongoTemplate.save(entity);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "purchaseOrderDocument",
          key = "{ 'findByCode', #entity.code }"),
      @CacheEvict(cacheNames = "purchaseOrderDocument",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "purchaseOrderDocument", key = "{ 'findAll' }")
  })
  public void delete(PurchaseOrderDocument entity) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(entity.getId()));

    mongoTemplate.remove(query, PurchaseOrderDocument.class);
  }

  @Override
  @CacheEvict(cacheNames = "purchaseOrderDocument", allEntries = true)
  public void deleteAll() {
    mongoTemplate.remove(new Query(), PurchaseOrderDocument.class);
  }

  @Override
  public long getCategoryRelated(String categoryId) {
    Query query = new Query(Criteria.where("purchaseOrderLines.categoryId").is(categoryId));

    return mongoTemplate.count(query, PurchaseOrderDocument.class);
  }
}
