#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.mapper;

import ${package}.${artifactId}.domain.model.domain.mongo.master.CategoryDocument;
import ${package}.${artifactId}.domain.model.exception.EntityMappingException;
import ${package}.${artifactId}.domain.ports.primary.dto.request.CategoryDto;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.master.CategoryDocumentRepository;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MasterDtoMongoMapper {

  @Autowired
  private CategoryDocumentRepository categoryDocumentRepository;

  @Mapping(source = "data.code", target = "code")
  @Mapping(source = "data.description", target = "description")
  public abstract CategoryDocument toCategoryDocument(CategoryDto dto);

  public abstract List<CategoryDocument> toCategoryDocumentList(List<CategoryDto> dto);

  @Mapping(source = "code", target = "data.code")
  @Mapping(source = "description", target = "data.description")
  public abstract CategoryDto toCategoryDto(CategoryDocument document);

  public abstract List<CategoryDto> toCategoryDtoList(List<CategoryDocument> document);

  @Named("getCategoryId")
  public String getCategoryId(String code)
      throws EntityMappingException {
    if (StringUtils.isNotBlank(code)) {
      CategoryDocument categoryEntity = categoryDocumentRepository.findByCode(code);
      if (categoryEntity == null) {
        throw new EntityMappingException("CategoryDocument not found");
      }
      return categoryEntity.getId();
    }
    return null;
  }

  @Named("getCategoryCode")
  public String getCategoryCode(String id)
      throws EntityMappingException {
    if (id != null) {
      CategoryDocument categoryEntity = categoryDocumentRepository.findById(id);
      if (categoryEntity == null) {
        throw new EntityMappingException("CategoryDocument not found");
      }
      return categoryEntity.getCode();
    }
    return null;
  }
}
