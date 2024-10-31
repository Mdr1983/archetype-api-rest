package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderLineDocument;
import com.mdemanuel.application.domain.model.exception.EntityMappingException;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDataDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PurchaseOrderDtoMongoMapperImpl extends PurchaseOrderDtoMongoMapper {

    @Autowired
    private MasterDtoMongoMapper masterDtoMongoMapper;

    @Override
    public PurchaseOrderDocument toPurchaseOrderDocument(PurchaseOrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseOrderDocument.PurchaseOrderDocumentBuilder<?, ?> purchaseOrderDocument = PurchaseOrderDocument.builder();

        purchaseOrderDocument.code( dtoDataCode( dto ) );
        purchaseOrderDocument.purchaseOrderDate( dtoDataPurchaseOrderDate( dto ) );
        List<PurchaseOrderLineDto> purchaseOrderLines = dtoDataPurchaseOrderLines( dto );
        purchaseOrderDocument.purchaseOrderLines( toPurchaseOrderLineDocumentList( purchaseOrderLines ) );

        return purchaseOrderDocument.build();
    }

    @Override
    public List<PurchaseOrderDocument> toPurchaseOrderDocumentList(List<PurchaseOrderDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<PurchaseOrderDocument> list = new ArrayList<PurchaseOrderDocument>( dto.size() );
        for ( PurchaseOrderDto purchaseOrderDto : dto ) {
            list.add( toPurchaseOrderDocument( purchaseOrderDto ) );
        }

        return list;
    }

    @Override
    public PurchaseOrderDto toPurchaseOrderDto(PurchaseOrderDocument document) {
        if ( document == null ) {
            return null;
        }

        PurchaseOrderDto.PurchaseOrderDtoBuilder<?, ?> purchaseOrderDto = PurchaseOrderDto.builder();

        purchaseOrderDto.data( purchaseOrderDocumentToPurchaseOrderDataDto( document ) );

        return purchaseOrderDto.build();
    }

    @Override
    public List<PurchaseOrderDto> toPurchaseOrderDtoList(List<PurchaseOrderDocument> document) {
        if ( document == null ) {
            return null;
        }

        List<PurchaseOrderDto> list = new ArrayList<PurchaseOrderDto>( document.size() );
        for ( PurchaseOrderDocument purchaseOrderDocument : document ) {
            list.add( toPurchaseOrderDto( purchaseOrderDocument ) );
        }

        return list;
    }

    @Override
    public PurchaseOrderLineDocument toPurchaseOrderLineDocument(PurchaseOrderLineDto dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseOrderLineDocument.PurchaseOrderLineDocumentBuilder<?, ?> purchaseOrderLineDocument = PurchaseOrderLineDocument.builder();

        try {
            purchaseOrderLineDocument.categoryId( masterDtoMongoMapper.getCategoryId( dto.getCategoryCode() ) );
        }
        catch ( EntityMappingException e ) {
            throw new RuntimeException( e );
        }
        purchaseOrderLineDocument.item( dto.getItem() );
        purchaseOrderLineDocument.description( dto.getDescription() );
        purchaseOrderLineDocument.quantity( dto.getQuantity() );

        return purchaseOrderLineDocument.build();
    }

    @Override
    public List<PurchaseOrderLineDocument> toPurchaseOrderLineDocumentList(List<PurchaseOrderLineDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<PurchaseOrderLineDocument> list = new ArrayList<PurchaseOrderLineDocument>( dto.size() );
        for ( PurchaseOrderLineDto purchaseOrderLineDto : dto ) {
            list.add( toPurchaseOrderLineDocument( purchaseOrderLineDto ) );
        }

        return list;
    }

    @Override
    public PurchaseOrderLineDto toPurchaseOrderLineDto(PurchaseOrderLineDocument document) {
        if ( document == null ) {
            return null;
        }

        PurchaseOrderLineDto.PurchaseOrderLineDtoBuilder<?, ?> purchaseOrderLineDto = PurchaseOrderLineDto.builder();

        try {
            purchaseOrderLineDto.categoryCode( masterDtoMongoMapper.getCategoryCode( document.getCategoryId() ) );
        }
        catch ( EntityMappingException e ) {
            throw new RuntimeException( e );
        }
        purchaseOrderLineDto.item( document.getItem() );
        purchaseOrderLineDto.description( document.getDescription() );
        purchaseOrderLineDto.quantity( document.getQuantity() );

        return purchaseOrderLineDto.build();
    }

    @Override
    public List<PurchaseOrderLineDto> toPurchaseOrderLineDtoList(List<PurchaseOrderLineDocument> document) {
        if ( document == null ) {
            return null;
        }

        List<PurchaseOrderLineDto> list = new ArrayList<PurchaseOrderLineDto>( document.size() );
        for ( PurchaseOrderLineDocument purchaseOrderLineDocument : document ) {
            list.add( toPurchaseOrderLineDto( purchaseOrderLineDocument ) );
        }

        return list;
    }

    private String dtoDataCode(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrderDataDto data = purchaseOrderDto.getData();
        if ( data == null ) {
            return null;
        }
        return data.getCode();
    }

    private Instant dtoDataPurchaseOrderDate(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrderDataDto data = purchaseOrderDto.getData();
        if ( data == null ) {
            return null;
        }
        return data.getPurchaseOrderDate();
    }

    private List<PurchaseOrderLineDto> dtoDataPurchaseOrderLines(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrderDataDto data = purchaseOrderDto.getData();
        if ( data == null ) {
            return null;
        }
        return data.getPurchaseOrderLines();
    }

    protected PurchaseOrderDataDto purchaseOrderDocumentToPurchaseOrderDataDto(PurchaseOrderDocument purchaseOrderDocument) {
        if ( purchaseOrderDocument == null ) {
            return null;
        }

        PurchaseOrderDataDto.PurchaseOrderDataDtoBuilder<?, ?> purchaseOrderDataDto = PurchaseOrderDataDto.builder();

        purchaseOrderDataDto.code( purchaseOrderDocument.getCode() );
        purchaseOrderDataDto.purchaseOrderDate( purchaseOrderDocument.getPurchaseOrderDate() );
        purchaseOrderDataDto.purchaseOrderLines( toPurchaseOrderLineDtoList( purchaseOrderDocument.getPurchaseOrderLines() ) );

        return purchaseOrderDataDto.build();
    }
}
