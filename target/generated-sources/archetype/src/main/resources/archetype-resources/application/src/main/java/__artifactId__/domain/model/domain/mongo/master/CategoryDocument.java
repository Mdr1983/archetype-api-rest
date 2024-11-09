#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.domain.mongo.master;

import ${package}.${artifactId}.domain.model.domain.BaseDocument;
import ${package}.${artifactId}.domain.model.util.DbDocuments;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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

@Document(collection = DbDocuments.CATEGORY)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryDocument extends BaseDocument {

  private static final long serialVersionUID = 1234297234577476811L;

  @Id
  private String id;

  @Indexed
  @Field(name = "code")
  @NotNull
  private String code;

  @Field(name = "description")
  @NotNull
  private String description;
}
