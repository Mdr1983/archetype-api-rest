package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryGenericDocument;
import com.mdemanuel.application.domain.model.exception.EntityMappingException;
import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MasterGenericDtoMongoMapper {

  @Autowired
  private GenericDocumentRepository<CategoryGenericDocument> categoryGenericDocumentRepository;

  public abstract CategoryGenericDocument toCategoryGenericDocument(GenericDto dto);

  public abstract List<CategoryGenericDocument> toCategoryGenericDocumentList(List<GenericDto> dto);

  public abstract GenericDto toGenericDto(CategoryGenericDocument document);

  public abstract List<GenericDto> toGenericDtoList(List<CategoryGenericDocument> document);

  @Named("getCategoryId")
  public String getCategoryId(String code)
      throws EntityMappingException {
    if (StringUtils.isNotBlank(code)) {
      CategoryGenericDocument categoryGenericDocument = categoryGenericDocumentRepository.findByCode(code);
      if (categoryGenericDocument == null) {
        throw new EntityMappingException("CategoryGenericDocument not found");
      }
      return categoryGenericDocument.getId();
    }
    return null;
  }

  @Named("getCategoryCode")
  public String getCategoryCode(String id)
      throws EntityMappingException {
    if (id != null) {
      CategoryGenericDocument categoryGenericDocument = categoryGenericDocumentRepository.findById(id)
          .orElse(null);
      if (categoryGenericDocument == null) {
        throw new EntityMappingException("CategoryGenericDocument not found");
      }
      return categoryGenericDocument.getData().get("code").toString();
    }
    return null;
  }
}
