#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.domain.mongo.audit;

import ${package}.${artifactId}.domain.model.domain.BaseDocument;
import ${package}.${artifactId}.domain.model.util.DbDocuments;
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

@Document(collection = DbDocuments.AUDIT_ENTRY)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuditEntryDocument extends BaseDocument {

  private static final long serialVersionUID = 1234297232017476811L;

  @Id
  private String id;

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

  @Field(name = "parameters")
  private String parameters;

  @Field(name = "request_body")
  private String requestBody;

  @Field(name = "response_body")
  private String responseBody;

  @Indexed
  @Field(name = "http_status")
  private int httpStatus;

  @Indexed
  @Field(name = "key_value")
  private String keyValue;

  @Field(name = "elapsed_time")
  private long elapsedTime;
}
