package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.master.CategoryEntity;
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

        categoryEntity.categoryCode( dto.getCategoryCode() );
        categoryEntity.description( dto.getDescription() );

        return categoryEntity.build();
    }

    @Override
    public List<CategoryEntity> toCategoryEntityList(List<CategoryDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<CategoryEntity> list = new ArrayList<CategoryEntity>( dto.size() );
        for ( CategoryDto categoryDto : dto ) {
            list.add( toCategoryEntity( categoryDto ) );
        }

        return list;
    }

    @Override
    public CategoryDto toCategoryDto(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder<?, ?> categoryDto = CategoryDto.builder();

        categoryDto.categoryCode( entity.getCategoryCode() );
        categoryDto.description( entity.getDescription() );

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
}
