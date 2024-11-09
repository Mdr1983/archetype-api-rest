package it.pkg.application.domain.model.domain.postgres.audit;

import it.pkg.application.domain.model.domain.BaseEntity;
import it.pkg.application.domain.model.util.DbTables;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Table(name = DbTables.AUDIT_ENTRY)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuditEntryEntity extends BaseEntity {

  private static final long serialVersionUID = 1200297232017476811L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "trace_id")
  private String traceId;

  @Column(name = "span_id")
  private String spanId;

  @Column(name = "url")
  private String url;

  @Column(name = "http_method")
  private String httpMethod;

  @Column(name = "parameters")
  private String parameters;

  @Type(JsonBinaryType.class)
  @Column(name = "request_body", columnDefinition = "jsonb")
  private Map<String, Object> requestBody;

  @Type(JsonBinaryType.class)
  @Column(name = "response_body", columnDefinition = "jsonb")
  private Map<String, Object> responseBody;

  @Column(name = "http_status")
  private int httpStatus;

  @Column(name = "key_value")
  private String keyValue;

  @Column(name = "elapsed_time")
  private long elapsedTime;
}
