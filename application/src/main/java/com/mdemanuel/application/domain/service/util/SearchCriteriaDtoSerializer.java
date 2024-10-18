package com.mdemanuel.application.domain.service.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import java.io.IOException;

public class SearchCriteriaDtoSerializer extends JsonSerializer<SearchCriteriaDto> {

  @Override
  public void serialize(SearchCriteriaDto value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("attribute", value.getAttribute());
    gen.writeStringField("operator", value.getOperator().name());
    gen.writeObjectField("valueList", value.getValueList());

    // Serializar childrenCriteriaGroups sin recursividad
    if (value.getChildrenCriteriaGroups() != null && value.getChildrenCriteriaGroups().getCriteria() != null
        && !value.getChildrenCriteriaGroups().getCriteria().isEmpty()) {
      gen.writeObjectField("childrenCriteriaGroups", value.getChildrenCriteriaGroups());
    }

    gen.writeEndObject();
  }
}