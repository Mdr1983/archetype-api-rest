package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderLineDocument;
import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MasterDtoMongoMapper.class)
public abstract class PurchaseOrderGenericDtoMongoMapper {

  public abstract PurchaseOrderGenericDocument toPurchaseOrderDocument(GenericDto dto);

  public abstract List<PurchaseOrderGenericDocument> toPurchaseOrderDocumentList(List<GenericDto> dto);

  public abstract GenericDto toGenericDto(PurchaseOrderGenericDocument document);

  public abstract List<GenericDto> toGenericDtoList(List<PurchaseOrderGenericDocument> document);

  @Mapping(source = "categoryCode", target = "categoryId", qualifiedByName = "getCategoryId")
  public abstract PurchaseOrderLineDocument toPurchaseOrderLineDocument(PurchaseOrderLineDto dto);

  public abstract List<PurchaseOrderLineDocument> toPurchaseOrderLineDocumentList(List<PurchaseOrderLineDto> dto);

  @Mapping(source = "categoryId", target = "categoryCode", qualifiedByName = "getCategoryCode")
  public abstract PurchaseOrderLineDto toPurchaseOrderLineDto(PurchaseOrderLineDocument document);

  public abstract List<PurchaseOrderLineDto> toPurchaseOrderLineDtoList(List<PurchaseOrderLineDocument> document);
}
