package it.pkg.application.domain.service.purchase_order;

import it.pkg.application.domain.ports.primary.dto.request.GenericDto;
import it.pkg.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import it.pkg.application.domain.service.exceptions.BadFormatException;
import it.pkg.application.domain.service.exceptions.DuplicatedItemException;
import it.pkg.application.domain.service.exceptions.ItemNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PurchaseOrderGenericMongoService {

  List<GenericDto> getAllPurchaseOrder();

  Page<GenericDto> getAllPurchaseOrder(SearchCriteriaDto dto);

  GenericDto getPurchaseOrder(String code)
      throws ItemNotFoundException;

  GenericDto addPurchaseOrder(GenericDto dto)
      throws DuplicatedItemException;

  GenericDto updatePurchaseOrder(GenericDto dto, String code)
      throws ItemNotFoundException, BadFormatException;

  void deletePurchaseOrder(String code)
      throws ItemNotFoundException;
}
