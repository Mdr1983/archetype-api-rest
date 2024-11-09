#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import java.io.IOException;

public class SearchCriteriaDtoSerializer extends JsonSerializer<SearchCriteriaDto> {

  @Override
  public void serialize(SearchCriteriaDto value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();

    gen.writeNumberField("size", value.getSize());
    gen.writeNumberField("page", value.getPage());
    gen.writeStringField("sortDirection", value.getSortDirection().toString());
    gen.writeStringField("sortField", value.getSortField());

    if (value.getSearchCriteriaGroup() != null) {
      gen.writeObjectField("searchCriteriaGroup", value.getSearchCriteriaGroup());
    }

    gen.writeEndObject();
  }
}