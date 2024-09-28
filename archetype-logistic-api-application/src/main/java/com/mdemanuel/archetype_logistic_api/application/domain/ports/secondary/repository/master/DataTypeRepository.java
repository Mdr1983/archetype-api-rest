package com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository.master;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.master.DataTypeEntity;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends CrudRepository<DataTypeEntity, Integer> {

  @Query(value = "SELECT * FROM data_type WHERE data_type_code = :code", nativeQuery = true)
  @Cacheable(cacheNames = "cache-day",
      key = "{ #root.methodName,'data_type', #code }",
      unless = "#result == null")
  DataTypeEntity findByCode(String code);

  @Override
  @Cacheable(cacheNames = "cache-day",
      key = "{ #root.methodName,'data_type', #id }",
      unless = "#result == null")
  Optional<DataTypeEntity> findById(Integer id);

  @Override
  @Cacheable(cacheNames = "cache-day",
      key = "{ #root.methodName,'data_type'}",
      unless = "#result == null")
  Iterable<DataTypeEntity> findAll();
}
