package com.louzao.application.domain.service.mapper;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.OutputVatEntity;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatDto;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.OutputVatRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OutputVatDtoMapper {

  @Autowired
  private OutputVatRepository outputVatRepository;

  public abstract OutputVatEntity toOutputVatEntity(OutputVatDto dto);

  public abstract List<OutputVatEntity> toOutputVatEntityList(List<OutputVatDto> dto);

  public abstract OutputVatDto toOutputVatDto(OutputVatEntity entity);

  public abstract List<OutputVatDto> toOutputVatDtoList(List<OutputVatEntity> entity);
}
