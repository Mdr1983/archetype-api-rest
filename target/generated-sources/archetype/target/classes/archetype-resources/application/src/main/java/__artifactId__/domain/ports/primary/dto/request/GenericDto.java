#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
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
public class GenericDto implements Serializable {

  private static final long serialVersionUID = 1268767234577476811L;

  @Schema(description = "Metadata in format json",
      required = true,
      example = "{${symbol_escape}n"
          + "  ${symbol_escape}"executedBy${symbol_escape}": ${symbol_escape}"testuser${symbol_escape}"${symbol_escape}n"
          + "}")
  @NotNull
  private Map<String, Object> metadata;
  @Schema(description = "Data in format json",
      required = true,
      example = "{${symbol_escape}n"
          + "  ${symbol_escape}"code${symbol_escape}": ${symbol_escape}"BOLSO${symbol_escape}",${symbol_escape}n"
          + "  ${symbol_escape}"description${symbol_escape}": ${symbol_escape}"Bolso piel${symbol_escape}"${symbol_escape}n"
          + "}")
  @NotNull
  private Map<String, Object> data;
}
