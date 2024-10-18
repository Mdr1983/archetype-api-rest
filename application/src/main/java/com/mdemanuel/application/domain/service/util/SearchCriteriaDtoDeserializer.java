package com.mdemanuel.application.domain.service.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto.CriteriaGroup;
import com.mdemanuel.application.domain.ports.primary.dto.request.pojo.OperatorsFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaDtoDeserializer extends JsonDeserializer<SearchCriteriaDto> {

  @Override
  public SearchCriteriaDto deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    ObjectMapper mapper = (ObjectMapper) p.getCodec();
    JsonNode node = mapper.readTree(p);

    // Crear una nueva instancia de SearchCriteriaDto
    SearchCriteriaDto searchCriteriaDto = new SearchCriteriaDto();

    // Leer y establecer el campo "attribute"
    JsonNode attributeNode = node.get("attribute");
    if (attributeNode != null) {
      searchCriteriaDto.setAttribute(attributeNode.asText());
    }

    // Leer y establecer el campo "operator"
    JsonNode operatorNode = node.get("operator");
    if (operatorNode != null) {
      searchCriteriaDto.setOperator(OperatorsFilter.valueOf(operatorNode.asText()));
    }

    // Leer y establecer el campo "valueList"
    JsonNode valueListNode = node.get("valueList");
    if (valueListNode != null && valueListNode.isArray()) {
      List<String> valueList = new ArrayList<>();
      for (JsonNode valueNode : valueListNode) {
        valueList.add(valueNode.asText());
      }
      searchCriteriaDto.setValueList(valueList);
    }

    // Leer y establecer el campo "childrenCriteriaGroups" sin recursividad
    JsonNode childrenCriteriaGroupsNode = node.get("childrenCriteriaGroups");
    if (childrenCriteriaGroupsNode != null) {
      CriteriaGroup childrenCriteriaGroups = mapper.treeToValue(childrenCriteriaGroupsNode, CriteriaGroup.class);
      searchCriteriaDto.setChildrenCriteriaGroups(childrenCriteriaGroups);
    }

    return searchCriteriaDto;
  }
}