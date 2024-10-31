package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderLineDocument;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MasterDtoMongoMapper.class)
public abstract class PurchaseOrderDtoMongoMapper {

  @Mapping(source = "data.code", target = "code")
  @Mapping(source = "data.purchaseOrderDate", target = "purchaseOrderDate")
  @Mapping(source = "data.purchaseOrderLines", target = "purchaseOrderLines")
  public abstract PurchaseOrderDocument toPurchaseOrderDocument(PurchaseOrderDto dto);

  public abstract List<PurchaseOrderDocument> toPurchaseOrderDocumentList(List<PurchaseOrderDto> dto);

  @Mapping(source = "code", target = "data.code")
  @Mapping(source = "purchaseOrderDate", target = "data.purchaseOrderDate")
  @Mapping(source = "purchaseOrderLines", target = "data.purchaseOrderLines")
  public abstract PurchaseOrderDto toPurchaseOrderDto(PurchaseOrderDocument document);

  public abstract List<PurchaseOrderDto> toPurchaseOrderDtoList(List<PurchaseOrderDocument> document);

  @Mapping(source = "categoryCode", target = "categoryId", qualifiedByName = "getCategoryId")
  public abstract PurchaseOrderLineDocument toPurchaseOrderLineDocument(PurchaseOrderLineDto dto);

  public abstract List<PurchaseOrderLineDocument> toPurchaseOrderLineDocumentList(List<PurchaseOrderLineDto> dto);

  @Mapping(source = "categoryId", target = "categoryCode", qualifiedByName = "getCategoryCode")
  public abstract PurchaseOrderLineDto toPurchaseOrderLineDto(PurchaseOrderLineDocument document);

  public abstract List<PurchaseOrderLineDto> toPurchaseOrderLineDtoList(List<PurchaseOrderLineDocument> document);
}
