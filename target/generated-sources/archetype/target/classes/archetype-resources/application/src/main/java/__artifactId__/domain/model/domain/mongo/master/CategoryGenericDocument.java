#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.domain.mongo.master;

import ${package}.${artifactId}.domain.model.domain.mongo.GenericDocument;
import ${package}.${artifactId}.domain.model.util.DbDocuments;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = DbDocuments.CATEGORY_GENERIC)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryGenericDocument extends GenericDocument implements Serializable {

  private static final long serialVersionUID = 1234297234577476811L;

  @Id
  private String id;

}
