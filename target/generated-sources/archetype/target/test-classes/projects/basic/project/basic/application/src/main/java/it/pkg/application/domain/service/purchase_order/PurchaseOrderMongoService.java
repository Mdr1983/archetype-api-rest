package it.pkg.application.domain.service.purchase_order;

import it.pkg.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import it.pkg.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import it.pkg.application.domain.service.exceptions.BadFormatException;
import it.pkg.application.domain.service.exceptions.DuplicatedItemException;
import it.pkg.application.domain.service.exceptions.ItemNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PurchaseOrderMongoService {

  List<PurchaseOrderDto> getAllPurchaseOrder();

  Page<PurchaseOrderDto> getAllPurchaseOrder(SearchCriteriaDto dto);

  PurchaseOrderDto getPurchaseOrder(String code)
      throws ItemNotFoundException;

  PurchaseOrderDto addPurchaseOrder(PurchaseOrderDto dto)
      throws DuplicatedItemException;

  PurchaseOrderDto updatePurchaseOrder(PurchaseOrderDto dto, String code)
      throws ItemNotFoundException, BadFormatException;

  void deletePurchaseOrder(String code)
      throws ItemNotFoundException;
}
