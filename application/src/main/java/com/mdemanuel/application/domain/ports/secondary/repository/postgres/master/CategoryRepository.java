package com.mdemanuel.application.domain.ports.secondary.repository.postgres.master;

import com.mdemanuel.application.domain.model.domain.postgres.master.CategoryEntity;
import com.mdemanuel.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>,
    JpaSpecificationExecutor<CategoryEntity> {

  @Cacheable(cacheNames = "category",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  CategoryEntity findByCode(String code);

  @Override
  @Cacheable(cacheNames = "category",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  Optional<CategoryEntity> findById(Integer id);

  @Override
  @Cacheable(cacheNames = "category",
      key = "{ #root.methodName}",
      unless = "#result == null")
  Iterable<CategoryEntity> findAll();

  @Override
  Page<CategoryEntity> findAll(Specification<CategoryEntity> spec, Pageable pageable);

  @Override
  @Caching(put = {
      @CachePut(cacheNames = "category",
          key = "{ 'findByCode', #result.code }",
          unless = "#result == null"),
      @CachePut(cacheNames = "category",
          key = "{ 'findById', #result.id }",
          unless = "#result == null")},
      evict = {
          @CacheEvict(cacheNames = "category", key = "{ 'findAll' }")})
  <S extends CategoryEntity> S save(S entity);

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "category",
          key = "{ 'findByCode', #entity.code }"),
      @CacheEvict(cacheNames = "category",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "category", key = "{ 'findAll' }")
  })
  void delete(CategoryEntity entity);

  @Override
  default void deleteById(Integer id) {
    CategoryRepository service = SpringBeanUtil.getInstance().getBean(CategoryRepository.class);

    Optional<CategoryEntity> entity = findById(id);
    if (entity.isPresent()) {
      service.delete(entity.get());
    } else {
      throw new EntityNotFoundException(String.format("Not exists %s: %s", CategoryEntity.class.getSimpleName(), id));
    }
  }

  @Override
  @CacheEvict(cacheNames = "category", allEntries = true)
  void deleteAll();
}