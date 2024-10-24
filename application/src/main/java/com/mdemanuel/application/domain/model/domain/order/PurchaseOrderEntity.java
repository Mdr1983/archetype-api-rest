package com.mdemanuel.application.domain.model.domain.order;

import com.mdemanuel.application.domain.model.domain.BaseEntity;
import com.mdemanuel.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.PURCHASE_ORDER)
@Entity
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PurchaseOrderEntity extends BaseEntity {

  private static final long serialVersionUID = 2360297231230476811L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "purchase_order_id")
  private Integer purchaseOrderId;

  @Column(name = "purchase_order_code")
  @NotNull
  private String purchaseOrderCode;

  @Column(name = "purchase_order_date")
  @NotNull
  private Instant purchaseOrderDate;

  @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.EAGER)
  private List<PurchaseOrderLineEntity> purchaseOrderLines = new ArrayList<>();
}
