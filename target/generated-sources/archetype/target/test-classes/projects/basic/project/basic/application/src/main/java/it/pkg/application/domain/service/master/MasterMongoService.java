package it.pkg.application.domain.service.master;

import it.pkg.application.domain.ports.primary.dto.request.CategoryDto;
import it.pkg.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import it.pkg.application.domain.service.exceptions.BadFormatException;
import it.pkg.application.domain.service.exceptions.DuplicatedItemException;
import it.pkg.application.domain.service.exceptions.ItemNotFoundException;
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
