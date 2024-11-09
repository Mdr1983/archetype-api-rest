#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.repository.postgres.purchase_order;

import ${package}.application.domain.model.domain.postgres.purchase_order.PurchaseOrderEntity;
import ${package}.application.domain.model.domain.postgres.purchase_order.PurchaseOrderLineEntity;
import ${package}.application.domain.ports.secondary.repository.postgres.purchase_order.PurchaseOrderRepository;
import ${package}.${artifactId}.repository.extractor.PurchaseOrderListServiceResultSetExtractor;
import ${package}.${artifactId}.repository.extractor.PurchaseOrderServiceResultSetExtractor;
import java.sql.Timestamp;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

  private static final String QUERY_FIND_BY_CODE = "SELECT * "
      + "FROM purchase_order p "
      + "LEFT JOIN purchase_order_line pol on pol.purchase_order_id = p.id "
      + "WHERE p.code = :code";

  private static final String QUERY_FIND_BY_ID = "SELECT * "
      + "FROM purchase_order p "
      + "LEFT JOIN purchase_order_line pol on pol.purchase_order_id = p.id "
      + "WHERE p.id = :id";

  private static final String QUERY_FIND_ALL = "SELECT * "
      + "FROM purchase_order p "
      + "LEFT JOIN purchase_order_line pol on pol.purchase_order_id = p.id";

  private static final String QUERY_INSERT_ORDER = "INSERT INTO purchase_order (code, purchase_order_date) "
      + "VALUES (:code, :purchase_order_date)";

  private static final String QUERY_UPDATE_ORDER = "UPDATE purchase_order "
      + "SET code = :code, purchase_order_date = :purchase_order_date "
      + "WHERE id = :id";

  private static final String QUERY_INSERT_ORDER_LINE = "INSERT INTO purchase_order_line (purchase_order_id, item, "
      + "description, category_id, quantity) "
      + "VALUES (:purchase_order_id, :item, :description, :category_id, :quantity)";

  private static final String QUERY_DELETE_PURCHASE_ORDER_BY_ID = "DELETE "
      + "FROM purchase_order "
      + "WHERE id = :id";

  private static final String QUERY_DELETE_PURCHASE_ORDER_LINE_BY_PURCHASE_ORDER_ID = "DELETE "
      + "FROM purchase_order_line "
      + "WHERE purchase_order_id = :purchase_order_id";

  private static final String QUERY_DELETE_PURCHASE_ORDER_ALL = "DELETE "
      + "FROM purchase_order";

  private static final String QUERY_DELETE_PURCHASE_ORDER_LINE_ALL = "DELETE "
      + "FROM purchase_order_line";

  @Autowired
  @Qualifier(value = "namedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Cacheable(cacheNames = "purchaseOrder",
      key = "{ ${symbol_pound}root.methodName, ${symbol_pound}code }",
      unless = "${symbol_pound}result == null")
  public PurchaseOrderEntity findByCode(String code) {
    try {
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("code", code, Types.VARCHAR);

      return namedParameterJdbcTemplate.query(QUERY_FIND_BY_CODE, namedParameters,
          new PurchaseOrderServiceResultSetExtractor());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  @Cacheable(cacheNames = "purchaseOrder",
      key = "{ ${symbol_pound}root.methodName, ${symbol_pound}id }",
      unless = "${symbol_pound}result == null")
  public PurchaseOrderEntity findById(Integer id) {
    try {
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("id", id, Types.INTEGER);

      return namedParameterJdbcTemplate.query(QUERY_FIND_BY_ID, namedParameters,
          new PurchaseOrderServiceResultSetExtractor());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  @Cacheable(cacheNames = "purchaseOrder",
      key = "{ ${symbol_pound}root.methodName }",
      unless = "${symbol_pound}result == null")
  public Iterable<PurchaseOrderEntity> findAll() {
    return namedParameterJdbcTemplate.query(QUERY_FIND_ALL, new MapSqlParameterSource(),
        new PurchaseOrderListServiceResultSetExtractor());
  }

  @Override
  public Page<PurchaseOrderEntity> findAll(Specification<PurchaseOrderEntity> spec, Pageable pageable) {
    return null;
  }

  @Caching(put = {
      @CachePut(cacheNames = "purchaseOrder",
          key = "{ 'findByCode', ${symbol_pound}result.code }",
          unless = "${symbol_pound}result == null"),
      @CachePut(cacheNames = "purchaseOrder",
          key = "{ 'findById', ${symbol_pound}result.id }",
          unless = "${symbol_pound}result == null")},
      evict = {
          @CacheEvict(cacheNames = "purchaseOrder", key = "{ 'findAll' }")})
  public PurchaseOrderEntity save(PurchaseOrderEntity entity) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("id", entity.getId(), Types.INTEGER);
    namedParameters.addValue("code", entity.getCode(), Types.VARCHAR);
    namedParameters.addValue("purchase_order_date", Timestamp.from(entity.getPurchaseOrderDate()),
        Types.TIMESTAMP);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    if (entity.getId() == null) {
      namedParameterJdbcTemplate.update(QUERY_INSERT_ORDER, namedParameters, keyHolder);
    } else {
      namedParameterJdbcTemplate.update(QUERY_UPDATE_ORDER, namedParameters, keyHolder);
    }

    // Obtener el ID generado
    Map<String, Object> generatedKeys = keyHolder.getKeys();
    Integer purchaseOrderId = (Integer) generatedKeys.get("id");
    entity.setId(purchaseOrderId);

    for (PurchaseOrderLineEntity item : entity.getPurchaseOrderLines()) {
      MapSqlParameterSource namedParameters2 = new MapSqlParameterSource();
      namedParameters2.addValue("purchase_order_id", purchaseOrderId, Types.INTEGER);
      namedParameters2.addValue("item", item.getItem(), Types.VARCHAR);
      namedParameters2.addValue("description", item.getDescription(), Types.VARCHAR);
      namedParameters2.addValue("category_id", item.getCategoryId(), Types.INTEGER);
      namedParameters2.addValue("quantity", item.getQuantity(), Types.INTEGER);

      KeyHolder keyHolder2 = new GeneratedKeyHolder();

      if (entity.getId() == null) {
        namedParameterJdbcTemplate.update(QUERY_DELETE_PURCHASE_ORDER_LINE_BY_PURCHASE_ORDER_ID, namedParameters2);

        namedParameterJdbcTemplate.update(QUERY_INSERT_ORDER_LINE, namedParameters2, keyHolder2);
      } else {
        namedParameterJdbcTemplate.update(QUERY_INSERT_ORDER_LINE, namedParameters2, keyHolder2);
      }

      // Obtener el ID generado
      Map<String, Object> generatedKeys2 = keyHolder2.getKeys();
      Integer purchaseOrderLineId = (Integer) generatedKeys2.get("id");
      item.setId(purchaseOrderLineId);
    }

    return entity;
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "purchaseOrder",
          key = "{ 'findByCode', ${symbol_pound}entity.code }"),
      @CacheEvict(cacheNames = "purchaseOrder",
          key = "{ 'findById', ${symbol_pound}entity.id }"),
      @CacheEvict(cacheNames = "purchaseOrder", key = "{ 'findAll' }")
  })
  public void delete(PurchaseOrderEntity entity) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("id", entity.getId(), Types.INTEGER);
    namedParameters.addValue("purchase_order_id", entity.getId(), Types.INTEGER);

    namedParameterJdbcTemplate.update(QUERY_DELETE_PURCHASE_ORDER_LINE_BY_PURCHASE_ORDER_ID, namedParameters);

    namedParameterJdbcTemplate.update(QUERY_DELETE_PURCHASE_ORDER_BY_ID, namedParameters);
  }

  @Override
  @CacheEvict(cacheNames = "purchaseOrder", allEntries = true)
  public void deleteAll() {
    namedParameterJdbcTemplate.update(QUERY_DELETE_PURCHASE_ORDER_LINE_ALL, new MapSqlParameterSource());

    namedParameterJdbcTemplate.update(QUERY_DELETE_PURCHASE_ORDER_ALL, new MapSqlParameterSource());
  }
}
