#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.primary.dto.response.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionErrorDto implements Serializable {

  private static final long serialVersionUID = 1236712300270169043L;

  @Schema(name = "errorCode", example = "002", required = true)
  private String errorCode;
  @Schema(name = "errorName", example = "Not found item", required = true)
  private String errorName;
  @Schema(name = "errorDescription", example = " Not exists CategoryEntity for Id: 1", required = true)
  private String errorDescription;
}
