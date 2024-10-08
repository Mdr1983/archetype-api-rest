package com.mdemanuel.application.domain.service.master.impl;

import com.mdemanuel.application.domain.model.domain.master.DataTypeEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.DataTypeDto;
import com.mdemanuel.application.domain.ports.secondary.repository.master.DataTypeRepository;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.mapper.MasterDtoMapper;
import com.mdemanuel.application.domain.service.master.MasterService;
import com.mdemanuel.application.domain.service.util.EntityService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {

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

    dataTypeRepository.save(masterDtoMapper.toDataTypeEntity(dto)).getDataTypeId();

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

    dataTypeRepository.save(masterDtoMapper.toDataTypeEntity(dto)).getDataTypeId();

    return dto;
  }

  @SneakyThrows
  @Override
  public void deleteDataType(String code) {
    entityService.getEntityByCode(code, DataTypeEntity.class, true);

    dataTypeRepository.deleteById(entityService.getEntityByCode(code, DataTypeEntity.class, true).getDataTypeId());
  }
}
