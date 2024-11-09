package it.pkg.application.domain.model.domain.mongo.purchase_order;

import it.pkg.application.domain.model.domain.BaseDocument;
import it.pkg.application.domain.model.util.DbDocuments;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
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

@Document(collection = DbDocuments.PURCHASE_ORDER)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PurchaseOrderDocument extends BaseDocument {

  private static final long serialVersionUID = 1234297231230476811L;

  @Id
  private String id;

  @Indexed
  @Field(name = "code")
  @NotNull
  private String code;

  @Field(name = "purchase_order_date")
  @NotNull
  private Instant purchaseOrderDate;

  @Field(name = "purchase_order_lines")
  private List<PurchaseOrderLineDocument> purchaseOrderLines;
}
