package com.louzao.secadapter.repository.mssql.autoline.audit;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditEntryEntity;
import com.louzao.application.domain.ports.secondary.repository.mssql.audit.AuditEntryRepository;
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
    namedParameters.addValue("request_body", entity.getRequestBody(), Types.VARBINARY);
    namedParameters.addValue("response_body", entity.getResponseBody(), Types.VARBINARY);
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
