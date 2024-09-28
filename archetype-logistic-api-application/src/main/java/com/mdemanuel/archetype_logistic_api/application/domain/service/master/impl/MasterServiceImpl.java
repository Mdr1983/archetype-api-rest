package com.mdemanuel.archetype_logistic_api.application.domain.service.master.impl;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.master.DataTypeEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.request.DataTypeDto;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository.master.DataTypeRepository;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.archetype_logistic_api.application.domain.service.mapper.MasterDtoMapper;
import com.mdemanuel.archetype_logistic_api.application.domain.service.master.MasterService;
import com.mdemanuel.archetype_logistic_api.application.domain.service.util.EntityService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {

  public static final String DTO_ENTITY_ID_NOT_MATCH_ERROR_MESSAGE = "The dto id does not match the entity id";

  @Autowired
  private DataTypeRepository dataTypeRepository;
  @Autowired
  private MasterDtoMapper masterDtoMapper;
  @Autowired
  private EntityService entityService;

  @Override
  public List<DataTypeDto> getAllDataType() {
    return masterDtoMapper.toDataTypeDtoList(new ArrayList<>((Collection) dataTypeRepository.findAll()));
  }

  @SneakyThrows
  @Override
  public DataTypeDto getDataType(String code) {
    return masterDtoMapper.toDataTypeDto(entityService.getEntityByCode(code, DataTypeEntity.class, true));
  }

  @SneakyThrows
  @Override
  public DataTypeDto addDataType(DataTypeDto dto) {
    DataTypeEntity entity = entityService.getEntityByCode(dto.getDataTypeCode(), DataTypeEntity.class, false);

    if (entity != null) {
      throw new DuplicatedItemException(DataTypeEntity.class.getSimpleName(), "Code", dto.getDataTypeCode());
    }

    dto.setDataTypeId(dataTypeRepository.save(masterDtoMapper.toDataTypeEntity(dto)).getDataTypeId());

    return dto;
  }

  @SneakyThrows
  @Override
  public DataTypeDto updateDataType(DataTypeDto dto, String code) {
    DataTypeEntity entity = entityService.getEntityByCode(code, DataTypeEntity.class, true);

    if (!code.equals(dto.getDataTypeCode())) {
      DataTypeEntity entity2 = entityService.getEntityByCode(dto.getDataTypeCode(), DataTypeEntity.class, false);

      if (entity2 != null) {
        throw new DuplicatedItemException(DataTypeEntity.class.getSimpleName(), "Code", dto.getDataTypeCode());
      }
    }

    if (!Objects.equals(entity.getDataTypeId(), dto.getDataTypeId())) {
      throw new BadFormatException(DTO_ENTITY_ID_NOT_MATCH_ERROR_MESSAGE);
    }

    dto.setDataTypeId(dataTypeRepository.save(masterDtoMapper.toDataTypeEntity(dto)).getDataTypeId());

    return dto;
  }

  @SneakyThrows
  @Override
  public void deleteDataType(String code) {
    entityService.getEntityByCode(code, DataTypeEntity.class, true);

    dataTypeRepository.deleteById(entityService.getEntityByCode(code, DataTypeEntity.class, true).getDataTypeId());
  }
}
