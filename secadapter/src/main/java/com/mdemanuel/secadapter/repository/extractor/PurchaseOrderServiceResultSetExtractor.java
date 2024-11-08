package com.mdemanuel.secadapter.repository.extractor;

import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderLineEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

@Slf4j
public class PurchaseOrderServiceResultSetExtractor implements ResultSetExtractor<PurchaseOrderEntity> {

  @Override
  public PurchaseOrderEntity extractData(ResultSet rs)
      throws SQLException, DataAccessException {
    log.debug("Ejecutando la Extracción de PurchaseOrder...");

    if (!rs.next()) {
      return null;
    }

    PurchaseOrderEntity result = PurchaseOrderEntity.builder().id(rs.getInt("id"))
        .code(rs.getString("code"))
        .purchaseOrderDate(rs.getTimestamp("purchase_order_date").toInstant())
        .purchaseOrderLines(new ArrayList<>())
        .build();

    do {
      result.getPurchaseOrderLines()
          .add(PurchaseOrderLineEntity.builder().id(rs.getInt("id")).item(rs.getString("item"))
              .description(rs.getString("description")).categoryId(rs.getInt("category_id"))
              .quantity(rs.getInt("quantity")).build());
    } while (rs.next());

    return result;
  }
}
