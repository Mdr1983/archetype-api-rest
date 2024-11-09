#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.purchase_order;

import ${package}.${artifactId}.domain.ports.primary.dto.request.PurchaseOrderDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.service.exceptions.BadFormatException;
import ${package}.${artifactId}.domain.service.exceptions.DuplicatedItemException;
import ${package}.${artifactId}.domain.service.exceptions.ItemNotFoundException;
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
