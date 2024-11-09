#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.master;

import ${package}.${artifactId}.domain.ports.primary.dto.request.CategoryDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.service.exceptions.BadFormatException;
import ${package}.${artifactId}.domain.service.exceptions.DuplicatedItemException;
import ${package}.${artifactId}.domain.service.exceptions.ItemNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;

public interface MasterMongoService {

  List<CategoryDto> getAllCategory();

  Page<CategoryDto> getAllCategory(SearchCriteriaDto dto);

  CategoryDto getCategory(String code)
      throws ItemNotFoundException;

  CategoryDto addCategory(CategoryDto dto)
      throws DuplicatedItemException;

  CategoryDto updateCategory(CategoryDto dto, String code)
      throws ItemNotFoundException, BadFormatException;

  void deleteCategory(String code)
      throws ItemNotFoundException;
}
