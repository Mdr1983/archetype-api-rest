package com.mdemanuel.application.domain.service.master;

import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;

public interface MasterService {

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
