#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.domain.postgres.purchase_order;

import ${package}.${artifactId}.domain.model.domain.BaseEntity;
import ${package}.${artifactId}.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.PURCHASE_ORDER_LINE)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PurchaseOrderLineEntity extends BaseEntity {

  private static final long serialVersionUID = 5430297231230476811L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "item")
  @NotNull
  private String item;

  @Column(name = "description")
  @NotNull
  private String description;

  @Column(name = "category_id")
  @NotNull
  private int categoryId;

  @Column(name = "quantity")
  @NotNull
  private int quantity;
}
