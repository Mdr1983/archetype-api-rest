package com.mdemanuel.application.domain.service.purchase_order;

import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
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
