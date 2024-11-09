#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.mapper;

import ${package}.${artifactId}.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import ${package}.${artifactId}.domain.model.domain.mongo.purchase_order.PurchaseOrderLineDocument;
import ${package}.${artifactId}.domain.ports.primary.dto.request.GenericDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.PurchaseOrderLineDto;
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
