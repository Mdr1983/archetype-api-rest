package it.pkg.secadapter.repository.postgres.master;

import it.pkg.application.domain.model.domain.postgres.master.CategoryEntity;
import it.pkg.application.domain.ports.secondary.repository.postgres.master.CategoryRepository;
import java.sql.Types;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

  private static final String QUERY_FIND_BY_CODE = "SELECT * "
      + "FROM category "
      + "WHERE code = :code";

  private static final String QUERY_FIND_BY_ID = "SELECT * "
      + "FROM category "
      + "WHERE id = :id";

  private static final String QUERY_FIND_ALL = "SELECT * "
      + "FROM category";

  private static final String QUERY_INSERT = "INSERT INTO category (code, description) "
      + "VALUES (:code, :description)";

  private static final String QUERY_DELETE_BY_ID = "DELETE "
      + "FROM category "
      + "WHERE id = :id";

  private static final String QUERY_DELETE_ALL = "DELETE "
      + "FROM category";

  @Autowired
  @Qualifier(value = "namedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Cacheable(cacheNames = "category",
      key = "{ #root.methodName, #code }",
      unless = "#result == null")
  @Override
  public CategoryEntity findByCode(String code) {
    try {
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("code", code, Types.VARCHAR);

      return namedParameterJdbcTemplate.queryForObject(QUERY_FIND_BY_CODE, namedParameters,
          new BeanPropertyRowMapper<>(CategoryEntity.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  @Cacheable(cacheNames = "category",
      key = "{ #root.methodName, #id }",
      unless = "#result == null")
  public CategoryEntity findById(Integer id) {
    try {
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("id", id, Types.INTEGER);

      return namedParameterJdbcTemplate.queryForObject(QUERY_FIND_BY_ID, namedParameters,
          new BeanPropertyRowMapper<>(CategoryEntity.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  @Cacheable(cacheNames = "category",
      key = "{ #root.methodName}",
      unless = "#result == null")
  public Iterable<CategoryEntity> findAll() {
    return namedParameterJdbcTemplate.query(QUERY_FIND_ALL, new MapSqlParameterSource(),
        new BeanPropertyRowMapper<>(CategoryEntity.class));
  }

  @Override
  public Page<CategoryEntity> findAll(Specification<CategoryEntity> spec, Pageable pageable) {
    return null;
  }

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
  public CategoryEntity save(CategoryEntity entity) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("code", entity.getCode(), Types.VARCHAR);
    namedParameters.addValue("description", entity.getDescription(), Types.VARCHAR);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(QUERY_INSERT, namedParameters, keyHolder);

    // Obtener el ID generado
    Map<String, Object> generatedKeys = keyHolder.getKeys();
    Integer categoryId = (Integer) generatedKeys.get("id");
    entity.setId(categoryId);

    return entity;
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "category",
          key = "{ 'findByCode', #entity.code }"),
      @CacheEvict(cacheNames = "category",
          key = "{ 'findById', #entity.id }"),
      @CacheEvict(cacheNames = "category", key = "{ 'findAll' }")
  })
  public void delete(CategoryEntity entity) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("id", entity.getId(), Types.INTEGER);

    namedParameterJdbcTemplate.update(QUERY_DELETE_BY_ID, namedParameters);
  }

  @Override
  @CacheEvict(cacheNames = "category", allEntries = true)
  public void deleteAll() {
    namedParameterJdbcTemplate.update(QUERY_DELETE_ALL, new MapSqlParameterSource());
  }
}
