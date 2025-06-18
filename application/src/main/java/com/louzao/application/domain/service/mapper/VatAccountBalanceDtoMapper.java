package com.louzao.application.domain.service.mapper;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.VatAccountBalanceEntity;
import com.louzao.application.domain.ports.primary.dto.request.VatAccountBalanceDto;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.VatAccountBalanceRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class VatAccountBalanceDtoMapper {

  @Autowired
  private VatAccountBalanceRepository vatAccountBalanceRepository;

  public abstract VatAccountBalanceEntity toVatAccountBalanceEntity(VatAccountBalanceDto dto);

  public abstract List<VatAccountBalanceEntity> toVatAccountBalanceEntityList(List<VatAccountBalanceDto> dto);

  public abstract VatAccountBalanceDto toVatAccountBalanceDto(VatAccountBalanceEntity entity);

  public abstract List<VatAccountBalanceDto> toVatAccountBalanceDtoList(List<VatAccountBalanceEntity> entity);
}
