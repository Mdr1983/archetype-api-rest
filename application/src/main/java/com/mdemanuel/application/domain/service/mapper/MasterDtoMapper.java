package com.mdemanuel.application.domain.service.mapper;

import com.mdemanuel.application.domain.model.domain.postgres.master.CategoryEntity;
import com.mdemanuel.application.domain.model.exception.EntityMappingException;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDataDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDto;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.master.CategoryRepository;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MasterDtoMapper {

  @Autowired
  private CategoryRepository categoryRepository;

  @Mapping(source = "data.code", target = "code")
  @Mapping(source = "data.description", target = "description")
  public abstract CategoryEntity toCategoryEntity(CategoryDto dto);

  public abstract List<CategoryEntity> toCategoryEntityList(List<CategoryDataDto> dto);

  @Mapping(source = "code", target = "data.code")
  @Mapping(source = "description", target = "data.description")
  public abstract CategoryDto toCategoryDto(CategoryEntity entity);

  public abstract List<CategoryDto> toCategoryDtoList(List<CategoryEntity> entity);

  @Named("getCategoryId")
  public Integer getCategoryId(String code)
      throws EntityMappingException {
    if (StringUtils.isNotBlank(code)) {
      CategoryEntity categoryEntity = categoryRepository.findByCode(code);
      if (categoryEntity == null) {
        throw new EntityMappingException("CategoryEntity not found");
      }
      return categoryEntity.getId();
    }
    return null;
  }

  @Named("getCategoryCode")
  public String getCategoryCode(Integer id)
      throws EntityMappingException {
    if (id != null) {
      CategoryEntity categoryEntity = categoryRepository.findById(id);
      if (categoryEntity == null) {
        throw new EntityMappingException("CategoryEntity not found");
      }
      return categoryEntity.getCode();
    }
    return null;
  }
}
