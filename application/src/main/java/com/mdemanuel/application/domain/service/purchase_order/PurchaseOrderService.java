package com.mdemanuel.application.domain.service.purchase_order;

import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
import java.util.List;

public interface PurchaseOrderService {

  List<PurchaseOrderDto> getAllPurchaseOrder();

  List<PurchaseOrderDto> getAllPurchaseOrder(SearchCriteriaDto dto);

  PurchaseOrderDto getPurchaseOrder(String code)
      throws ItemNotFoundException;

  PurchaseOrderDto addPurchaseOrder(PurchaseOrderDto dto)
      throws DuplicatedItemException;

  PurchaseOrderDto updatePurchaseOrder(PurchaseOrderDto dto, String code)
      throws ItemNotFoundException, BadFormatException;

  void deletePurchaseOrder(String code)
      throws ItemNotFoundException;
}
