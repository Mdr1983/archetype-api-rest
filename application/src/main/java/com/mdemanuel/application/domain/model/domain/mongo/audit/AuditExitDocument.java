package com.mdemanuel.application.domain.model.domain.mongo.audit;

import com.mdemanuel.application.domain.model.domain.BaseDocument;
import com.mdemanuel.application.domain.model.util.DbDocuments;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = DbDocuments.AUDIT_EXIT)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuditExitDocument extends BaseDocument {

  private static final long serialVersionUID = 1234297234577476811L;

  @Id
  private String id;

  @Field(name = "audit_entry_id")
  private String auditEntryId;

  @Indexed
  @Field(name = "trace_id")
  private String traceId;

  @Field(name = "span_id")
  private String spanId;

  @Indexed
  @Field(name = "url")
  private String url;

  @Field(name = "http_method")
  private String httpMethod;

  @Field(name = "request_body")
  private byte[] requestBody;

  @Field(name = "response_body")
  private byte[] responseBody;

  @Indexed
  @Field(name = "status")
  private int status;

  @Field(name = "elapsed_time")
  private long elapsedTime;
}
