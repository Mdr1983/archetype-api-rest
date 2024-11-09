package it.pkg.application.domain.model.domain.mongo.purchase_order;

import it.pkg.application.domain.model.domain.mongo.GenericDocument;
import it.pkg.application.domain.model.util.DbDocuments;
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

@Document(collection = DbDocuments.PURCHASE_ORDER_GENERIC)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PurchaseOrderGenericDocument extends GenericDocument implements Serializable {

  private static final long serialVersionUID = 1231200234577476811L;

  @Id
  private String id;

}
