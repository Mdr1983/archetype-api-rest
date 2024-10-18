package com.mdemanuel.application.domain.ports.primary.dto.request;

import com.mdemanuel.application.domain.ports.primary.dto.request.pojo.OperatorsFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SearchCriteriaDto implements Serializable {

  private static final long serialVersionUID = 1230621268512408522L;

  @Schema(description = "Attribute",
      required = false,
      example = "categoryCode")
  private String attribute;
  @Schema(description = "Operator",
      required = true,
      example = "EQUALS")
  private OperatorsFilter operator;
  @Schema(description = "List of values",
      required = false,
      example = "")
  private List<String> valueList;
  @Schema(description = "List of criterians",
      required = false,
      example = "")
  private CriteriaGroup childrenCriteriaGroups;

  @Data
  public static class CriteriaGroup implements Serializable {

    private static final long serialVersionUID = 1230621222312408522L;

    private List<SearchCriteriaDto> criteria;
  }
}
