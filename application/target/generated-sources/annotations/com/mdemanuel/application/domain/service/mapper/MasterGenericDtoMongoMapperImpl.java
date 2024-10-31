package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryGenericDocument;
import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class MasterGenericDtoMongoMapperImpl extends MasterGenericDtoMongoMapper {

    @Override
    public CategoryGenericDocument toCategoryGenericDocument(GenericDto dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryGenericDocument.CategoryGenericDocumentBuilder<?, ?> categoryGenericDocument = CategoryGenericDocument.builder();

        Map<String, Object> map = dto.getMetadata();
        if ( map != null ) {
            categoryGenericDocument.metadata( new LinkedHashMap<String, Object>( map ) );
        }
        Map<String, Object> map1 = dto.getData();
        if ( map1 != null ) {
            categoryGenericDocument.data( new LinkedHashMap<String, Object>( map1 ) );
        }

        return categoryGenericDocument.build();
    }

    @Override
    public List<CategoryGenericDocument> toCategoryGenericDocumentList(List<GenericDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<CategoryGenericDocument> list = new ArrayList<CategoryGenericDocument>( dto.size() );
        for ( GenericDto genericDto : dto ) {
            list.add( toCategoryGenericDocument( genericDto ) );
        }

        return list;
    }

    @Override
    public GenericDto toGenericDto(CategoryGenericDocument document) {
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
    public List<GenericDto> toGenericDtoList(List<CategoryGenericDocument> document) {
        if ( document == null ) {
            return null;
        }

        List<GenericDto> list = new ArrayList<GenericDto>( document.size() );
        for ( CategoryGenericDocument categoryGenericDocument : document ) {
            list.add( toGenericDto( categoryGenericDocument ) );
        }

        return list;
    }
}
