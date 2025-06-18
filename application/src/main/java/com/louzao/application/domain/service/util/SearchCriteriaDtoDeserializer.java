package com.louzao.application.domain.service.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louzao.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.louzao.application.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto;
import java.io.IOException;
import org.springframework.data.domain.Sort.Direction;

public class SearchCriteriaDtoDeserializer extends JsonDeserializer<SearchCriteriaDto> {

  @Override
  public SearchCriteriaDto deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    ObjectMapper mapper = (ObjectMapper) p.getCodec();
    JsonNode node = mapper.readTree(p);

    // Crear una nueva instancia de SearchCriteriaDto
    SearchCriteriaDto searchCriteriaDto = new SearchCriteriaDto();

    // Leer y establecer el campo "size"
    JsonNode sizeNode = node.get("size");
    if (sizeNode != null) {
      searchCriteriaDto.setSize(sizeNode.asInt());
    }

    // Leer y establecer el campo "page"
    JsonNode pageNode = node.get("page");
    if (pageNode != null) {
      searchCriteriaDto.setPage(pageNode.asInt());
    }

    // Leer y establecer el campo "sortDirection"
    JsonNode sortDirectionNode = node.get("sortDirection");
    if (sortDirectionNode != null) {
      searchCriteriaDto.setSortDirection(Direction.valueOf(sortDirectionNode.asText()));
    }

    // Leer y establecer el campo "sortField"
    JsonNode sortField = node.get("sortField");
    if (sortField != null) {
      searchCriteriaDto.setSortField(sortField.asText());
    }

    // Leer y establecer el campo "searchCriteriaGroup" sin recursividad
    JsonNode searchCriteriaGroupNode = node.get("searchCriteriaGroup");
    if (searchCriteriaGroupNode != null) {
      SearchCriteriaGroupDto searchCriteriaGroup = mapper.treeToValue(searchCriteriaGroupNode,
          SearchCriteriaGroupDto.class);
      searchCriteriaDto.setSearchCriteriaGroup(searchCriteriaGroup);
    }

    return searchCriteriaDto;
  }
}
