package com.mdemanuel.application.domain.service.master;

import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
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
