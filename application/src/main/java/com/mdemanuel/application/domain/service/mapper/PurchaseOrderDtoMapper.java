package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderLineEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MasterDtoMapper.class)
public interface PurchaseOrderDtoMapper {

  PurchaseOrderEntity toPurchaseOrderEntity(PurchaseOrderDto dto);

  List<PurchaseOrderEntity> toPurchaseOrderEntityList(List<PurchaseOrderDto> dto);

  PurchaseOrderDto toPurchaseOrderDto(PurchaseOrderEntity entity);

  List<PurchaseOrderDto> toPurchaseOrderDtoList(List<PurchaseOrderEntity> entity);

  @Mapping(source = "categoryCode", target = "categoryId", qualifiedByName = "getCategoryId")
  PurchaseOrderLineEntity toPurchaseOrderLineEntity(PurchaseOrderLineDto dto);

  List<PurchaseOrderLineEntity> toPurchaseOrderLineEntityList(List<PurchaseOrderLineDto> dto);

  @Mapping(source = "categoryId", target = "categoryCode", qualifiedByName = "getCategoryCode")
  PurchaseOrderLineDto toPurchaseOrderLineDto(PurchaseOrderLineEntity entity);

  List<PurchaseOrderLineDto> toPurchaseOrderLineDtoList(List<PurchaseOrderLineEntity> entity);

}
