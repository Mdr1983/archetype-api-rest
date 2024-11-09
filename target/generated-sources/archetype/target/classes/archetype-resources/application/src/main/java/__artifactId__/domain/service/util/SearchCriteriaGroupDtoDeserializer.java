#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto.CriteriaGroupDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.pojo.OperatorsFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaGroupDtoDeserializer extends JsonDeserializer<SearchCriteriaGroupDto> {

  @Override
  public SearchCriteriaGroupDto deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    ObjectMapper mapper = (ObjectMapper) p.getCodec();
    JsonNode node = mapper.readTree(p);

    // Crear una nueva instancia de SearchCriteriaGroupDto
    SearchCriteriaGroupDto searchCriteriaGroupDto = new SearchCriteriaGroupDto();

    // Leer y establecer el campo "attribute"
    JsonNode attributeNode = node.get("attribute");
    if (attributeNode != null) {
      searchCriteriaGroupDto.setAttribute(attributeNode.asText());
    }

    // Leer y establecer el campo "operator"
    JsonNode operatorNode = node.get("operator");
    if (operatorNode != null) {
      searchCriteriaGroupDto.setOperator(OperatorsFilter.valueOf(operatorNode.asText()));
    }

    // Leer y establecer el campo "valueList"
    JsonNode valueListNode = node.get("valueList");
    if (valueListNode != null && valueListNode.isArray()) {
      List<String> valueList = new ArrayList<>();
      for (JsonNode valueNode : valueListNode) {
        valueList.add(valueNode.asText());
      }
      searchCriteriaGroupDto.setValueList(valueList);
    }

    // Leer y establecer el campo "childrenCriteriaGroup" sin recursividad
    JsonNode criteriaGroupNode = node.get("criteriaGroup");
    if (criteriaGroupNode != null) {
      CriteriaGroupDto criteriaGroup = mapper.treeToValue(criteriaGroupNode, CriteriaGroupDto.class);
      searchCriteriaGroupDto.setCriteriaGroup(criteriaGroup);
    }

    return searchCriteriaGroupDto;
  }
}