package com.mdemanuel.archetype_logistic_api.application.domain.service.mapper;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.master.DataTypeEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.request.DataTypeDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public abstract class MasterDtoMapper {

  public abstract DataTypeEntity toDataTypeEntity(DataTypeDto dto);

  public abstract List<DataTypeEntity> toDataTypeEntityList(List<DataTypeDto> dto);

  public abstract DataTypeDto toDataTypeDto(DataTypeEntity entity);

  public abstract List<DataTypeDto> toDataTypeDtoList(List<DataTypeEntity> entity);
}
