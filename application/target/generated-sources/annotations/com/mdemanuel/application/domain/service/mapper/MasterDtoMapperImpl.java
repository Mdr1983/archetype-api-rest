package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.postgres.master.CategoryEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDataDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDto;
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
    public CategoryEntity toCategoryEntity(CategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder<?, ?> categoryEntity = CategoryEntity.builder();

        categoryEntity.code( dtoDataCode( dto ) );
        categoryEntity.description( dtoDataDescription( dto ) );

        return categoryEntity.build();
    }

    @Override
    public List<CategoryEntity> toCategoryEntityList(List<CategoryDataDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<CategoryEntity> list = new ArrayList<CategoryEntity>( dto.size() );
        for ( CategoryDataDto categoryDataDto : dto ) {
            list.add( categoryDataDtoToCategoryEntity( categoryDataDto ) );
        }

        return list;
    }

    @Override
    public CategoryDto toCategoryDto(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder<?, ?> categoryDto = CategoryDto.builder();

        categoryDto.data( categoryEntityToCategoryDataDto( entity ) );

        return categoryDto.build();
    }

    @Override
    public List<CategoryDto> toCategoryDtoList(List<CategoryEntity> entity) {
        if ( entity == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( entity.size() );
        for ( CategoryEntity categoryEntity : entity ) {
            list.add( toCategoryDto( categoryEntity ) );
        }

        return list;
    }

    private String dtoDataCode(CategoryDto categoryDto) {
        CategoryDataDto data = categoryDto.getData();
        if ( data == null ) {
            return null;
        }
        return data.getCode();
    }

    private String dtoDataDescription(CategoryDto categoryDto) {
        CategoryDataDto data = categoryDto.getData();
        if ( data == null ) {
            return null;
        }
        return data.getDescription();
    }

    protected CategoryEntity categoryDataDtoToCategoryEntity(CategoryDataDto categoryDataDto) {
        if ( categoryDataDto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder<?, ?> categoryEntity = CategoryEntity.builder();

        categoryEntity.code( categoryDataDto.getCode() );
        categoryEntity.description( categoryDataDto.getDescription() );

        return categoryEntity.build();
    }

    protected CategoryDataDto categoryEntityToCategoryDataDto(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryDataDto.CategoryDataDtoBuilder<?, ?> categoryDataDto = CategoryDataDto.builder();

        categoryDataDto.code( categoryEntity.getCode() );
        categoryDataDto.description( categoryEntity.getDescription() );

        return categoryDataDto.build();
    }
}
