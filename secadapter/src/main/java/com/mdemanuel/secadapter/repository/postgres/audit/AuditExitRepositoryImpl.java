package com.mdemanuel.secadapter.repository.postgres.audit;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditExitEntity;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit.AuditExitRepository;
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
public class AuditExitRepositoryImpl implements AuditExitRepository {

  private static final String QUERY_INSERT =
      "INSERT INTO audit_exit (audit_entry_id, trace_id, span_id, url, http_method, request_body, response_body, "
          + "http_status, elapsed_time) "
          + "VALUES (:audit_entry_id, :trace_id, :span_id, :url, :http_method, :request_body, :response_body, "
          + ":http_status, :elapsed_time)";

  @Autowired
  @Qualifier(value = "namedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public AuditExitEntity save(AuditExitEntity entity) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("audit_entry_id", entity.getAuditEntryId(), Types.INTEGER);
    namedParameters.addValue("trace_id", entity.getTraceId(), Types.VARCHAR);
    namedParameters.addValue("span_id", entity.getSpanId(), Types.VARCHAR);
    namedParameters.addValue("url", entity.getUrl(), Types.VARCHAR);
    namedParameters.addValue("http_method", entity.getHttpMethod(), Types.VARCHAR);
    namedParameters.addValue("request_body", entity.getRequestBody(), Types.VARBINARY);
    namedParameters.addValue("response_body", entity.getResponseBody(), Types.VARBINARY);
    namedParameters.addValue("http_status", entity.getHttpStatus(), Types.SMALLINT);
    namedParameters.addValue("elapsed_time", entity.getElapsedTime(), Types.INTEGER);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(QUERY_INSERT, namedParameters, keyHolder);

    // Obtener el ID generado
    Map<String, Object> generatedKeys = keyHolder.getKeys();
    Integer auditExitId = (Integer) generatedKeys.get("id");
    entity.setId(auditExitId);

    return entity;
  }
}
