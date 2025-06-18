package com.louzao.application.domain.service.mapper;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.InputVatEntity;
import com.louzao.application.domain.ports.primary.dto.request.InputVatDto;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.InputVatRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InputVatDtoMapper {

  @Autowired
  private InputVatRepository inputVatRepository;

  public abstract InputVatEntity toInputVatEntity(InputVatDto dto);

  public abstract List<InputVatEntity> toInputVatEntityList(List<InputVatDto> dto);

  public abstract InputVatDto toInputVatDto(InputVatEntity entity);

  public abstract List<InputVatDto> toInputVatDtoList(List<InputVatEntity> entity);
}
