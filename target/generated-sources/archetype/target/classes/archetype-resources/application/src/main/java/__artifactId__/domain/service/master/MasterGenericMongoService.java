#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.master;

import ${package}.${artifactId}.domain.ports.primary.dto.request.GenericDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.service.exceptions.BadFormatException;
import ${package}.${artifactId}.domain.service.exceptions.DuplicatedItemException;
import ${package}.${artifactId}.domain.service.exceptions.ItemNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;

public interface MasterGenericMongoService {

  List<GenericDto> getAllCategory();

  Page<GenericDto> getAllCategory(SearchCriteriaDto dto);

  GenericDto getCategory(String code)
      throws ItemNotFoundException;

  GenericDto addCategory(GenericDto dto)
      throws DuplicatedItemException;

  GenericDto updateCategory(GenericDto dto, String code)
      throws ItemNotFoundException, BadFormatException;

  void deleteCategory(String code)
      throws ItemNotFoundException;
}
