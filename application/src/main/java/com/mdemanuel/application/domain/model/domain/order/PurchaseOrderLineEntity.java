package com.mdemanuel.application.domain.model.domain.order;

import com.mdemanuel.application.domain.model.domain.BaseEntity;
import com.mdemanuel.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
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
  @Column(name = "purchase_order_line_id")
  private Integer purchaseOrderLineId;

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

  @ManyToOne
  @JoinColumn(name = "purchase_order_id", nullable = false)
  private PurchaseOrderEntity purchaseOrder;
}
