package com.mdemanuel.application.domain.ports.secondary.repository.mongo.master;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
import com.mdemanuel.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDocumentRepository extends MongoRepository<CategoryDocument, String> {

  @Query("{ code: ?0 }")
  @Cacheable(cacheNames = "categoryDocument",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  CategoryDocument findByCode(String code);

  @Override
  @Cacheable(cacheNames = "categoryDocument",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  Optional<CategoryDocument> findById(String id);

  @Override
  @Cacheable(cacheNames = "categoryDocument",
      key = "{ #root.methodName}",
      unless = "#result == null")
  List<CategoryDocument> findAll();

  @Override
  @Caching(put = {
      @CachePut(cacheNames = "categoryDocument",
          key = "{ 'findByCode', #result.code }",
          unless = "#result == null"),
      @CachePut(cacheNames = "categoryDocument",
          key = "{ 'findById', #result.id }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "categoryDocument", key = "{ 'findAll' }")})
  <S extends CategoryDocument> S save(S entity);

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "categoryDocument",
          key = "{ 'findByCode', #entity.code }"),
      @CacheEvict(cacheNames = "categoryDocument",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "categoryDocument", key = "{ 'findAll' }")
  })
  void delete(CategoryDocument entity);

  @Override
  default void deleteById(String id) {
    CategoryDocumentRepository service = SpringBeanUtil.getInstance().getBean(CategoryDocumentRepository.class);

    Optional<CategoryDocument> entity = findById(id);
    if (entity.isPresent()) {
      service.delete(entity.get());
    } else {
      throw new EntityNotFoundException(String.format("Not exists %s: %s", CategoryDocument.class.getSimpleName(), id));
    }
  }

  @Override
  @CacheEvict(cacheNames = "categoryDocument", allEntries = true)
  void deleteAll();
}
