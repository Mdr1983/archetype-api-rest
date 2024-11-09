#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto;
import java.io.IOException;

public class SearchCriteriaGroupDtoSerializer extends JsonSerializer<SearchCriteriaGroupDto> {

  @Override
  public void serialize(SearchCriteriaGroupDto value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();

    gen.writeStringField("attribute", value.getAttribute());
    gen.writeStringField("attribute", value.getAttribute());
    gen.writeStringField("operator", value.getOperator().name());
    gen.writeObjectField("valueList", value.getValueList());

    // Serializar criteriaGroup sin recursividad
    if (value.getCriteriaGroup() != null && value.getCriteriaGroup().getSearchCriteriaGroup() != null
        && !value.getCriteriaGroup().getSearchCriteriaGroup().isEmpty()) {
      gen.writeObjectField("criteriaGroup", value.getCriteriaGroup());
    }

    gen.writeEndObject();
  }
}