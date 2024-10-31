package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderLineDocument;
import com.mdemanuel.application.domain.model.exception.EntityMappingException;
import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PurchaseOrderGenericDtoMongoMapperImpl extends PurchaseOrderGenericDtoMongoMapper {

    @Autowired
    private MasterDtoMongoMapper masterDtoMongoMapper;

    @Override
    public PurchaseOrderGenericDocument toPurchaseOrderDocument(GenericDto dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseOrderGenericDocument.PurchaseOrderGenericDocumentBuilder<?, ?> purchaseOrderGenericDocument = PurchaseOrderGenericDocument.builder();

        Map<String, Object> map = dto.getMetadata();
        if ( map != null ) {
            purchaseOrderGenericDocument.metadata( new LinkedHashMap<String, Object>( map ) );
        }
        Map<String, Object> map1 = dto.getData();
        if ( map1 != null ) {
            purchaseOrderGenericDocument.data( new LinkedHashMap<String, Object>( map1 ) );
        }

        return purchaseOrderGenericDocument.build();
    }

    @Override
    public List<PurchaseOrderGenericDocument> toPurchaseOrderDocumentList(List<GenericDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<PurchaseOrderGenericDocument> list = new ArrayList<PurchaseOrderGenericDocument>( dto.size() );
        for ( GenericDto genericDto : dto ) {
            list.add( toPurchaseOrderDocument( genericDto ) );
        }

        return list;
    }

    @Override
    public GenericDto toGenericDto(PurchaseOrderGenericDocument document) {
        if ( document == null ) {
            return null;
        }

        GenericDto.GenericDtoBuilder<?, ?> genericDto = GenericDto.builder();

        Map<String, Object> map = document.getMetadata();
        if ( map != null ) {
            genericDto.metadata( new LinkedHashMap<String, Object>( map ) );
        }
        Map<String, Object> map1 = document.getData();
        if ( map1 != null ) {
            genericDto.data( new LinkedHashMap<String, Object>( map1 ) );
        }

        return genericDto.build();
    }

    @Override
    public List<GenericDto> toGenericDtoList(List<PurchaseOrderGenericDocument> document) {
        if ( document == null ) {
            return null;
        }

        List<GenericDto> list = new ArrayList<GenericDto>( document.size() );
        for ( PurchaseOrderGenericDocument purchaseOrderGenericDocument : document ) {
            list.add( toGenericDto( purchaseOrderGenericDocument ) );
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
}
