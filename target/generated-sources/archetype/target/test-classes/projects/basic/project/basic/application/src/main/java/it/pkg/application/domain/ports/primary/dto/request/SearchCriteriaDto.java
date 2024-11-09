package it.pkg.application.domain.ports.primary.dto.request;

import it.pkg.application.domain.ports.primary.dto.request.pojo.OperatorsFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort.Direction;

@Data
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SearchCriteriaDto implements Serializable {

  private static final long serialVersionUID = 1230621268512408522L;

  @Schema(description = "Size",
      required = false,
      example = "15")
  private Integer size;
  @Schema(description = "Page",
      required = false,
      example = "0")
  private Integer page;
  @Schema(description = "Sort direction",
      required = false,
      example = "ASC")
  private Direction sortDirection;
  @Schema(description = "Sort field",
      required = false,
      example = "categoryCode")
  private String sortField;
  @Schema(description = "Search criterians",
      required = false,
      example = "")
  private SearchCriteriaGroupDto searchCriteriaGroup;

  @Data
  public static class SearchCriteriaGroupDto implements Serializable {

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
    private CriteriaGroupDto criteriaGroup;

    @Data
    public static class CriteriaGroupDto implements Serializable {

      private static final long serialVersionUID = 1230621222312408522L;

      private List<SearchCriteriaGroupDto> searchCriteriaGroup;
    }
  }
}
