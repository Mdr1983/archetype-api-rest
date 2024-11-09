#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.purchase_order;

import ${package}.${artifactId}.domain.ports.primary.dto.request.GenericDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.service.exceptions.BadFormatException;
import ${package}.${artifactId}.domain.service.exceptions.DuplicatedItemException;
import ${package}.${artifactId}.domain.service.exceptions.ItemNotFoundException;
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
