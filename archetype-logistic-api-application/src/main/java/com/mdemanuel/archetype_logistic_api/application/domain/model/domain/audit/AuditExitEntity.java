package com.mdemanuel.archetype_logistic_api.application.domain.model.domain.audit;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.BaseEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = DbTables.AUDIT_EXIT)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuditExitEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "audit_exit_id")
  private Long auditExitId;

  @Column(name = "audit_entry_id")
  private Long auditEntryId;

  @Column(name = "trace_id")
  private String traceId;

  @Column(name = "span_id")
  private String spanId;

  @Column(name = "url")
  private String url;

  @Column(name = "http_method")
  private String httpMethod;

  @Column(name = "request")
  private byte[] request;

  @Column(name = "response")
  private byte[] response;

  @Column(name = "status")
  private int status;

  @Column(name = "elapsed_time")
  private long elapsedTime;
}
