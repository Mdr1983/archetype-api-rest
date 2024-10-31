package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderLineDocument;
import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.model.domain.postgres.purchase_order.PurchaseOrderLineEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDataDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MasterDtoMapper.class)
public interface PurchaseOrderDtoMapper {

  @Mapping(source = "data.code", target = "code")
  @Mapping(source = "data.purchaseOrderDate", target = "purchaseOrderDate")
  @Mapping(source = "data.purchaseOrderLines", target = "purchaseOrderLines")
  PurchaseOrderEntity toPurchaseOrderEntity(PurchaseOrderDto dto);

  List<PurchaseOrderEntity> toPurchaseOrderEntityList(List<PurchaseOrderDataDto> dto);

  @Mapping(source = "code", target = "data.code")
  @Mapping(source = "purchaseOrderDate", target = "data.purchaseOrderDate")
  @Mapping(source = "purchaseOrderLines", target = "data.purchaseOrderLines")
  PurchaseOrderDto toPurchaseOrderDto(PurchaseOrderEntity entity);

  List<PurchaseOrderDto> toPurchaseOrderDtoList(List<PurchaseOrderEntity> entity);

  @Mapping(source = "categoryCode", target = "categoryId", qualifiedByName = "getCategoryId")
  public abstract PurchaseOrderLineEntity toPurchaseOrderLineEntity(PurchaseOrderLineDto dto);

  public abstract List<PurchaseOrderLineEntity> toPurchaseOrderLineEntityList(List<PurchaseOrderLineDto> dto);

  @Mapping(source = "categoryId", target = "categoryCode", qualifiedByName = "getCategoryCode")
  public abstract PurchaseOrderLineDto toPurchaseOrderLineDto(PurchaseOrderLineEntity entity);

  public abstract List<PurchaseOrderLineDto> toPurchaseOrderLineDtoList(List<PurchaseOrderLineEntity> entity);
}
