#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.primary.dto.response.controller;

import java.io.Serializable;

public class ApiExceptionResponseDto extends ApiResponseDto<ExceptionErrorDto> implements Serializable {

  private static final long serialVersionUID = 1236723011270169043L;

  public ApiExceptionResponseDto(int httpStatusCode, String httpReasonPhrase, String instance, ExceptionErrorDto data) {
    super(httpStatusCode, httpReasonPhrase, instance, data);
  }
}
