package com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import com.mdemanuel.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDocumentRepository extends MongoRepository<PurchaseOrderDocument, String> {

  @Query("{ code: ?0 }")
  @Cacheable(cacheNames = "purchaseOrderDocument",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  PurchaseOrderDocument findByCode(String code);

  @Override
  @Cacheable(cacheNames = "purchaseOrderDocument",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  Optional<PurchaseOrderDocument> findById(String id);

  @Override
  @Cacheable(cacheNames = "purchaseOrderDocument",
      key = "{ #root.methodName }",
      unless = "#result == null")
  List<PurchaseOrderDocument> findAll();

  Page<PurchaseOrderDocument> findBy(Criteria criteria, Pageable pageable);

  @Caching(put = {
      @CachePut(cacheNames = "purchaseOrderDocument",
          key = "{ 'findByCode', #result.getCode() }",
          unless = "#result == null"),
      @CachePut(cacheNames = "purchaseOrderDocument",
          key = "{ 'findById', #result.getId() }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "purchaseOrderDocument", key = "{ 'findAll' }")})
  <S extends PurchaseOrderDocument> S save(S entity);

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "purchaseOrderDocument",
          key = "{ 'findByCode', #entity.code }"),
      @CacheEvict(cacheNames = "purchaseOrderDocument",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "purchaseOrderDocument", key = "{ 'findAll' }")
  })
  void delete(PurchaseOrderDocument entity);

  @Override
  default void deleteById(String id) {
    PurchaseOrderDocumentRepository service = SpringBeanUtil.getInstance()
        .getBean(PurchaseOrderDocumentRepository.class);

    Optional<PurchaseOrderDocument> entity = findById(id);
    if (entity.isPresent()) {
      service.delete(entity.get());
    } else {
      throw new EntityNotFoundException(
          String.format("Not exists %s: %s", PurchaseOrderDocument.class.getSimpleName(), id));
    }
  }

  @Override
  @CacheEvict(cacheNames = "purchaseOrderDocument", allEntries = true)
  void deleteAll();
}
