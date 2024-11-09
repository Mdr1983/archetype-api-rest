#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.domain.mongo.purchase_order;

import ${package}.${artifactId}.domain.model.domain.BaseDocument;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PurchaseOrderLineDocument extends BaseDocument {

  private static final long serialVersionUID = 1234297231230476811L;

  @Field(name = "item")
  @NotNull
  private String item;

  @Field(name = "description")
  @NotNull
  private String description;

  @Field(name = "category_id")
  @NotNull
  private String categoryId;

  @Field(name = "quantity")
  @NotNull
  private int quantity;
}
