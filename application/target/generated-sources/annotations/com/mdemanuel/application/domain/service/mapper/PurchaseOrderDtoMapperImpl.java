package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderLineEntity;
import com.mdemanuel.application.domain.model.exception.EntityMappingException;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderLineDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PurchaseOrderDtoMapperImpl implements PurchaseOrderDtoMapper {

    @Autowired
    private MasterDtoMapper masterDtoMapper;

    @Override
    public PurchaseOrderEntity toPurchaseOrderEntity(PurchaseOrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseOrderEntity.PurchaseOrderEntityBuilder<?, ?> purchaseOrderEntity = PurchaseOrderEntity.builder();

        purchaseOrderEntity.purchaseOrderCode( dto.getPurchaseOrderCode() );
        purchaseOrderEntity.purchaseOrderDate( dto.getPurchaseOrderDate() );
        purchaseOrderEntity.purchaseOrderLines( toPurchaseOrderLineEntityList( dto.getPurchaseOrderLines() ) );

        return purchaseOrderEntity.build();
    }

    @Override
    public List<PurchaseOrderEntity> toPurchaseOrderEntityList(List<PurchaseOrderDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<PurchaseOrderEntity> list = new ArrayList<PurchaseOrderEntity>( dto.size() );
        for ( PurchaseOrderDto purchaseOrderDto : dto ) {
            list.add( toPurchaseOrderEntity( purchaseOrderDto ) );
        }

        return list;
    }

    @Override
    public PurchaseOrderDto toPurchaseOrderDto(PurchaseOrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PurchaseOrderDto.PurchaseOrderDtoBuilder<?, ?> purchaseOrderDto = PurchaseOrderDto.builder();

        purchaseOrderDto.purchaseOrderCode( entity.getPurchaseOrderCode() );
        purchaseOrderDto.purchaseOrderDate( entity.getPurchaseOrderDate() );
        purchaseOrderDto.purchaseOrderLines( toPurchaseOrderLineDtoList( entity.getPurchaseOrderLines() ) );

        return purchaseOrderDto.build();
    }

    @Override
    public List<PurchaseOrderDto> toPurchaseOrderDtoList(List<PurchaseOrderEntity> entity) {
        if ( entity == null ) {
            return null;
        }

        List<PurchaseOrderDto> list = new ArrayList<PurchaseOrderDto>( entity.size() );
        for ( PurchaseOrderEntity purchaseOrderEntity : entity ) {
            list.add( toPurchaseOrderDto( purchaseOrderEntity ) );
        }

        return list;
    }

    @Override
    public PurchaseOrderLineEntity toPurchaseOrderLineEntity(PurchaseOrderLineDto dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseOrderLineEntity.PurchaseOrderLineEntityBuilder<?, ?> purchaseOrderLineEntity = PurchaseOrderLineEntity.builder();

        try {
            purchaseOrderLineEntity.categoryId( masterDtoMapper.getCategoryId( dto.getCategoryCode() ) );
        }
        catch ( EntityMappingException e ) {
            throw new RuntimeException( e );
        }
        purchaseOrderLineEntity.item( dto.getItem() );
        purchaseOrderLineEntity.description( dto.getDescription() );
        purchaseOrderLineEntity.quantity( dto.getQuantity() );

        return purchaseOrderLineEntity.build();
    }

    @Override
    public List<PurchaseOrderLineEntity> toPurchaseOrderLineEntityList(List<PurchaseOrderLineDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<PurchaseOrderLineEntity> list = new ArrayList<PurchaseOrderLineEntity>( dto.size() );
        for ( PurchaseOrderLineDto purchaseOrderLineDto : dto ) {
            list.add( toPurchaseOrderLineEntity( purchaseOrderLineDto ) );
        }

        return list;
    }

    @Override
    public PurchaseOrderLineDto toPurchaseOrderLineDto(PurchaseOrderLineEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PurchaseOrderLineDto.PurchaseOrderLineDtoBuilder<?, ?> purchaseOrderLineDto = PurchaseOrderLineDto.builder();

        try {
            purchaseOrderLineDto.categoryCode( masterDtoMapper.getCategoryCode( entity.getCategoryId() ) );
        }
        catch ( EntityMappingException e ) {
            throw new RuntimeException( e );
        }
        purchaseOrderLineDto.item( entity.getItem() );
        purchaseOrderLineDto.description( entity.getDescription() );
        purchaseOrderLineDto.quantity( entity.getQuantity() );

        return purchaseOrderLineDto.build();
    }

    @Override
    public List<PurchaseOrderLineDto> toPurchaseOrderLineDtoList(List<PurchaseOrderLineEntity> entity) {
        if ( entity == null ) {
            return null;
        }

        List<PurchaseOrderLineDto> list = new ArrayList<PurchaseOrderLineDto>( entity.size() );
        for ( PurchaseOrderLineEntity purchaseOrderLineEntity : entity ) {
            list.add( toPurchaseOrderLineDto( purchaseOrderLineEntity ) );
        }

        return list;
    }
}
