package com.louzao.application.domain.service.mapper;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.OutputVatConciliationEntity;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatConciliationDto;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.OutputVatRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OutputVatConciliationDtoMapper {

  @Autowired
  private OutputVatRepository outputVatRepository;

  public abstract OutputVatConciliationEntity toOutputVatConciliationEntity(OutputVatConciliationDto dto);

  public abstract List<OutputVatConciliationEntity> toOutputVatConciliationEntityList(
      List<OutputVatConciliationDto> dto);

  public abstract OutputVatConciliationDto toOutputVatConciliationDto(OutputVatConciliationEntity entity);

  public abstract List<OutputVatConciliationDto> toOutputVatConciliationDtoList(
      List<OutputVatConciliationEntity> entity);
}
