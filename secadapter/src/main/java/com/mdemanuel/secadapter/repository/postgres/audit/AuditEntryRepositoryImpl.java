package com.mdemanuel.secadapter.repository.postgres.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditEntryEntity;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit.AuditEntryRepository;
import java.sql.Types;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AuditEntryRepositoryImpl implements AuditEntryRepository {

  @Autowired
  private ObjectMapper objectMapper;

  private static final String QUERY_INSERT = "INSERT INTO audit_entry (trace_id, span_id, url, http_method, "
      + "parameters, request_body, response_body, http_status, key_value, elapsed_time) "
      + "VALUES (:trace_id, :span_id, :url, :http_method, :parameters, :request_body, :response_body, :http_status, "
      + ":key_value, :elapsed_time)";

  @Autowired
  @Qualifier(value = "namedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public AuditEntryEntity save(AuditEntryEntity entity) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("trace_id", entity.getTraceId(), Types.VARCHAR);
    namedParameters.addValue("span_id", entity.getSpanId(), Types.VARCHAR);
    namedParameters.addValue("url", entity.getUrl(), Types.VARCHAR);
    namedParameters.addValue("http_method", entity.getHttpMethod(), Types.VARCHAR);
    namedParameters.addValue("parameters", entity.getParameters(), Types.VARCHAR);
    namedParameters.addValue("request_body", objectMapper.valueToTree(entity.getRequestBody()).toString(),
        Types.OTHER);
    namedParameters.addValue("response_body", objectMapper.valueToTree(entity.getResponseBody()).toString(),
        Types.OTHER);
    namedParameters.addValue("http_status", entity.getHttpStatus(), Types.SMALLINT);
    namedParameters.addValue("key_value", entity.getKeyValue(), Types.VARCHAR);
    namedParameters.addValue("elapsed_time", entity.getElapsedTime(), Types.INTEGER);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(QUERY_INSERT, namedParameters, keyHolder);

    // Obtener el ID generado
    Map<String, Object> generatedKeys = keyHolder.getKeys();
    Integer auditEntryId = (Integer) generatedKeys.get("id");
    entity.setId(auditEntryId);

    return entity;
  }
}
