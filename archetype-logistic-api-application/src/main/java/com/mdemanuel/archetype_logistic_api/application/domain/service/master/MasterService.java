package com.mdemanuel.archetype_logistic_api.application.domain.service.master;

import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.request.DataTypeDto;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.ItemNotFoundException;
import java.util.List;

public interface MasterService {

  List<DataTypeDto> getAllDataType();

  DataTypeDto getDataType(String code)
      throws ItemNotFoundException;

  DataTypeDto addDataType(DataTypeDto dto)
      throws DuplicatedItemException;

  DataTypeDto updateDataType(DataTypeDto dto, String code)
      throws ItemNotFoundException, BadFormatException;

  void deleteDataType(String code)
      throws ItemNotFoundException;
}
