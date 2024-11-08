package com.mdemanuel.application.domain.ports.secondary.repository.mongo;

import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericDocumentRepository<T> {

  T findByCode(String code);

  T findById(String id);

  List<T> findAll();

  Page<T> findAll(DocumentMongoSpecification documentMongoSpecification, Pageable pageable);

  <S extends T> S save(S entity);

  void delete(T entity);

  void deleteById(String id);

  void deleteAll();

  long getCategoryRelated(String categoryId);
}
