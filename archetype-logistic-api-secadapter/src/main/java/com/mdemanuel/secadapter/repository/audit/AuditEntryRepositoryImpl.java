package com.mdemanuel.secadapter.repository.audit;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.audit.AuditEntryEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.model.exception.ExceptionCode;
import com.mdemanuel.archetype_logistic_api.application.domain.model.exception.SqlBadExecution;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository.audit.AuditEntryRepository;
import java.sql.Types;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AuditEntryRepositoryImpl implements AuditEntryRepository {

  private static final String QUERY =
      "INSERT INTO audit_entry (trace_id, span_id, url, http_method, parameters, request_body, response_body, "
          + "http_status, key_value, elapsed_time, created_at, updated_at) "
          + "VALUES (:traceId, :spanId, :url, :httpMethod, :parameters, :requestBody, :responseBody, :httpStatus,"
          + " :keyValue, :elapsedTime, :createdAt, :updatedAt)";

  @Autowired
  @Qualifier("namedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public int save(AuditEntryEntity auditEntryEntity)
      throws SqlBadExecution {
    try {
      log.debug("Init SQL for save AuditEntryEntity");

      MapSqlParameterSource parameters = new MapSqlParameterSource()
          .addValue("traceId", auditEntryEntity.getTraceId(), Types.VARCHAR)
          .addValue("spanId", auditEntryEntity.getSpanId(), Types.VARCHAR)
          .addValue("url", auditEntryEntity.getUrl(), Types.VARCHAR)
          .addValue("httpMethod", auditEntryEntity.getHttpMethod(), Types.VARCHAR)
          .addValue("parameters", auditEntryEntity.getParameters(), Types.VARCHAR)
          .addValue("requestBody", auditEntryEntity.getRequestBody(), Types.OTHER)
          .addValue("responseBody", auditEntryEntity.getResponseBody(), Types.OTHER)
          .addValue("httpStatus", auditEntryEntity.getHttpStatus(), Types.SMALLINT)
          .addValue("keyValue", auditEntryEntity.getKeyValue(), Types.VARCHAR)
          .addValue("elapsedTime", auditEntryEntity.getElapsedTime(), Types.INTEGER)
          .addValue("createdAt",
              (auditEntryEntity.getCreatedAt() != null) ? java.sql.Timestamp.from(auditEntryEntity.getCreatedAt())
                  : null,
              Types.TIMESTAMP)
          .addValue("updatedAt",
              (auditEntryEntity.getUpdatedAt() != null) ? java.sql.Timestamp.from(auditEntryEntity.getUpdatedAt())
                  : null,
              Types.TIMESTAMP);

      return namedParameterJdbcTemplate.update(QUERY, parameters);
    } catch (Exception e) {
      log.error(ExceptionCode.SQL_BAD_EXECUTION.getCode() + "-" + ExceptionCode.SQL_BAD_EXECUTION.getName() + " "
          + e.getMessage(), e);
      throw new SqlBadExecution(e.getLocalizedMessage());
    }
  }
}
