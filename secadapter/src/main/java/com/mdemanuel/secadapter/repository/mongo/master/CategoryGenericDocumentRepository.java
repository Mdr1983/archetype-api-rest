package com.mdemanuel.secadapter.repository.mongo.master;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryGenericDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import com.mdemanuel.application.domain.ports.secondary.repository.MongoCriteriaPageableQuery;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import com.mdemanuel.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
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
public class CategoryGenericDocumentRepository implements GenericDocumentRepository<CategoryGenericDocument> {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MongoCriteriaPageableQuery mongoCriteriaPageableQuery;

  @Override
  @Cacheable(cacheNames = "categoryGenericDocument",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  public CategoryGenericDocument findByCode(String code) {
    Query query = new Query();
    query.addCriteria(Criteria.where("data.code").is(code));

    return mongoTemplate.findOne(query, CategoryGenericDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "categoryGenericDocument",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  public CategoryGenericDocument findById(String id) {
    return mongoTemplate.findById(id, CategoryGenericDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "categoryGenericDocument",
      key = "{ #root.methodName}",
      unless = "#result == null")
  public List<CategoryGenericDocument> findAll() {
    return mongoTemplate.findAll(CategoryGenericDocument.class);
  }

  @Override
  public Page<CategoryGenericDocument> findAll(DocumentMongoSpecification documentMongoSpecification,
      Pageable pageable) {
    return mongoCriteriaPageableQuery.find(documentMongoSpecification, pageable);
  }

  @Override
  @Caching(put = {
      @CachePut(cacheNames = "categoryGenericDocument",
          key = "{ 'findByCode', #result.getData().get('code') }",
          unless = "#result == null"),
      @CachePut(cacheNames = "categoryGenericDocument",
          key = "{ 'findById', #result.id }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "categoryGenericDocument", key = "{ 'findAll' }")})
  public <S extends CategoryGenericDocument> S save(S entity) {
    return mongoTemplate.save(entity);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "categoryGenericDocument",
          key = "{ 'findByCode', #entity.getData().get('code') }"),
      @CacheEvict(cacheNames = "categoryGenericDocument",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "categoryGenericDocument", key = "{ 'findAll' }")
  })
  public void delete(CategoryGenericDocument entity) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(entity.getId()));

    mongoTemplate.remove(query, CategoryGenericDocument.class);
  }

  @Override
  public void deleteById(String id) {
    CategoryGenericDocumentRepository service = SpringBeanUtil.getInstance()
        .getBean(CategoryGenericDocumentRepository.class);

    CategoryGenericDocument entity = findById(id);
    if (entity != null) {
      service.delete(entity);
    } else {
      throw new EntityNotFoundException(
          String.format("Not exists %s: %s", GenericDocumentRepository.class.getSimpleName(), id));
    }
  }

  @Override
  @CacheEvict(cacheNames = "categoryGenericDocument", allEntries = true)
  public void deleteAll() {
    mongoTemplate.remove(new Query(), CategoryGenericDocument.class);
  }

  @Override
  public long getCategoryRelated(String categoryId) {
    return 0;
  }
}
