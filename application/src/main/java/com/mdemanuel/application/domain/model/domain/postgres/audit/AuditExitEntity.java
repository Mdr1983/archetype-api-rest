package com.mdemanuel.application.domain.model.domain.postgres.audit;

import com.mdemanuel.application.domain.model.domain.BaseEntity;
import com.mdemanuel.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.AUDIT_EXIT)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuditExitEntity extends BaseEntity {

  private static final long serialVersionUID = 1206297234577476811L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "audit_entry_id")
  private Integer auditEntryId;

  @Column(name = "trace_id")
  private String traceId;

  @Column(name = "span_id")
  private String spanId;

  @Column(name = "url")
  private String url;

  @Column(name = "http_method")
  private String httpMethod;

  @Column(name = "request_body")
  private byte[] requestBody;

  @Column(name = "response_body")
  private byte[] responseBody;

  @Column(name = "http_status")
  private int httpStatus;

  @Column(name = "elapsed_time")
  private long elapsedTime;
}
