package com.louzao.application.domain.service.mapper;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.InputVatConciliationEntity;
import com.louzao.application.domain.ports.primary.dto.request.InputVatConciliationDto;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.InputVatConciliationRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InputVatConciliationDtoMapper {

  @Autowired
  private InputVatConciliationRepository inputVatConciliationRepository;

  public abstract InputVatConciliationEntity toInputVatConciliationEntity(InputVatConciliationDto dto);

  public abstract List<InputVatConciliationEntity> toInputVatConciliationEntityList(List<InputVatConciliationDto> dto);

  public abstract InputVatConciliationDto toInputVatConciliationDto(InputVatConciliationEntity entity);

  public abstract List<InputVatConciliationDto> toInputVatConciliationDtoList(List<InputVatConciliationEntity> entity);
}
