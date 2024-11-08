package com.mdemanuel.secadapter.repository.mongo.purchase_order;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import com.mdemanuel.application.domain.ports.secondary.repository.MongoCriteriaPageableQuery;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import com.mdemanuel.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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
public class PurchaseOrderGenericDocumentRepository implements
    GenericDocumentRepository<PurchaseOrderGenericDocument> {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MongoCriteriaPageableQuery mongoCriteriaPageableQuery;

  @Override
  @Cacheable(cacheNames = "purchaseOrderGenericDocument",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  public PurchaseOrderGenericDocument findByCode(String code) {
    Query query = new Query();
    query.addCriteria(Criteria.where("data.code").is(code));

    return mongoTemplate.findOne(query, PurchaseOrderGenericDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "purchaseOrderGenericDocument",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  public Optional<PurchaseOrderGenericDocument> findById(String id) {
    return Optional.ofNullable(mongoTemplate.findById(id, PurchaseOrderGenericDocument.class));
  }

  @Override
  @Cacheable(cacheNames = "purchaseOrderGenericDocument",
      key = "{ #root.methodName}",
      unless = "#result == null")
  public List<PurchaseOrderGenericDocument> findAll() {
    return mongoTemplate.findAll(PurchaseOrderGenericDocument.class);
  }

  @Override
  public Page<PurchaseOrderGenericDocument> findAll(DocumentMongoSpecification documentMongoSpecification,
      Pageable pageable) {
    return mongoCriteriaPageableQuery.find(documentMongoSpecification, pageable);
  }

  @Override
  @Caching(put = {
      @CachePut(cacheNames = "purchaseOrderGenericDocument",
          key = "{ 'findByCode', #result.getData().get('code') }",
          unless = "#result == null"),
      @CachePut(cacheNames = "purchaseOrderGenericDocument",
          key = "{ 'findById', #result.id }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "purchaseOrderGenericDocument", key = "{ 'findAll' }")})
  public <S extends PurchaseOrderGenericDocument> S save(S entity) {
    return mongoTemplate.save(entity);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "purchaseOrderGenericDocument",
          key = "{ 'findByCode', #entity.getData().get('code') }"),
      @CacheEvict(cacheNames = "purchaseOrderGenericDocument",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "purchaseOrderGenericDocument", key = "{ 'findAll' }")
  })
  public void delete(PurchaseOrderGenericDocument entity) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(entity.getId()));

    mongoTemplate.remove(query, PurchaseOrderGenericDocument.class);
  }

  @Override
  public void deleteById(String id) {
    PurchaseOrderGenericDocumentRepository service = SpringBeanUtil.getInstance()
        .getBean(PurchaseOrderGenericDocumentRepository.class);

    Optional<PurchaseOrderGenericDocument> entity = findById(id);
    if (entity.isPresent()) {
      service.delete(entity.get());
    } else {
      throw new EntityNotFoundException(
          String.format("Not exists %s: %s", PurchaseOrderGenericDocument.class.getSimpleName(), id));
    }
  }

  @Override
  @CacheEvict(cacheNames = "purchaseOrderGenericDocument", allEntries = true)
  public void deleteAll() {
    mongoTemplate.remove(new Query(), PurchaseOrderGenericDocument.class);
  }
}
