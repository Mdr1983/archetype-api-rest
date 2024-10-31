package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
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
public class MasterDtoMongoMapperImpl extends MasterDtoMongoMapper {

    @Override
    public CategoryDocument toCategoryDocument(CategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryDocument.CategoryDocumentBuilder<?, ?> categoryDocument = CategoryDocument.builder();

        categoryDocument.code( dtoDataCode( dto ) );
        categoryDocument.description( dtoDataDescription( dto ) );

        return categoryDocument.build();
    }

    @Override
    public List<CategoryDocument> toCategoryDocumentList(List<CategoryDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<CategoryDocument> list = new ArrayList<CategoryDocument>( dto.size() );
        for ( CategoryDto categoryDto : dto ) {
            list.add( toCategoryDocument( categoryDto ) );
        }

        return list;
    }

    @Override
    public CategoryDto toCategoryDto(CategoryDocument document) {
        if ( document == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder<?, ?> categoryDto = CategoryDto.builder();

        categoryDto.data( categoryDocumentToCategoryDataDto( document ) );

        return categoryDto.build();
    }

    @Override
    public List<CategoryDto> toCategoryDtoList(List<CategoryDocument> document) {
        if ( document == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( document.size() );
        for ( CategoryDocument categoryDocument : document ) {
            list.add( toCategoryDto( categoryDocument ) );
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

    protected CategoryDataDto categoryDocumentToCategoryDataDto(CategoryDocument categoryDocument) {
        if ( categoryDocument == null ) {
            return null;
        }

        CategoryDataDto.CategoryDataDtoBuilder<?, ?> categoryDataDto = CategoryDataDto.builder();

        categoryDataDto.code( categoryDocument.getCode() );
        categoryDataDto.description( categoryDocument.getDescription() );

        return categoryDataDto.build();
    }
}
