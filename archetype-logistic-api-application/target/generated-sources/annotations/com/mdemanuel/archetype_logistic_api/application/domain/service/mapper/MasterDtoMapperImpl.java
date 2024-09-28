package com.mdemanuel.archetype_logistic_api.application.domain.service.mapper;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.master.DataTypeEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.request.DataTypeDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class MasterDtoMapperImpl extends MasterDtoMapper {

    @Override
    public DataTypeEntity toDataTypeEntity(DataTypeDto dto) {
        if ( dto == null ) {
            return null;
        }

        DataTypeEntity.DataTypeEntityBuilder<?, ?> dataTypeEntity = DataTypeEntity.builder();

        dataTypeEntity.dataTypeId( dto.getDataTypeId() );
        dataTypeEntity.dataTypeCode( dto.getDataTypeCode() );
        dataTypeEntity.description( dto.getDescription() );

        return dataTypeEntity.build();
    }

    @Override
    public List<DataTypeEntity> toDataTypeEntityList(List<DataTypeDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<DataTypeEntity> list = new ArrayList<DataTypeEntity>( dto.size() );
        for ( DataTypeDto dataTypeDto : dto ) {
            list.add( toDataTypeEntity( dataTypeDto ) );
        }

        return list;
    }

    @Override
    public DataTypeDto toDataTypeDto(DataTypeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DataTypeDto.DataTypeDtoBuilder<?, ?> dataTypeDto = DataTypeDto.builder();

        if ( entity.getDataTypeId() != null ) {
            dataTypeDto.dataTypeId( entity.getDataTypeId() );
        }
        dataTypeDto.dataTypeCode( entity.getDataTypeCode() );
        dataTypeDto.description( entity.getDescription() );

        return dataTypeDto.build();
    }

    @Override
    public List<DataTypeDto> toDataTypeDtoList(List<DataTypeEntity> entity) {
        if ( entity == null ) {
            return null;
        }

        List<DataTypeDto> list = new ArrayList<DataTypeDto>( entity.size() );
        for ( DataTypeEntity dataTypeEntity : entity ) {
            list.add( toDataTypeDto( dataTypeEntity ) );
        }

        return list;
    }
}
