#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.api.adapter;

import ${package}.application.domain.model.exception.BaseException;
import ${package}.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import ${package}.application.domain.ports.primary.dto.response.controller.ExceptionErrorDto;
import ${package}.application.domain.service.exceptions.ExceptionCode;
import java.lang.reflect.UndeclaredThrowableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  private void printLog(Exception ex, String message) {
    if (ex instanceof BaseException) {
      log.warn(message);
    } else {
      log.error(message);
    }
  }

  @ExceptionHandler({UndeclaredThrowableException.class})
  public final ResponseEntity<Object> handleUndeclaredThrowableException(Exception ex, WebRequest request) {
    UndeclaredThrowableException uex = (UndeclaredThrowableException) ex;

    return handleProcessExecutedException((Exception) uex.getUndeclaredThrowable(), request);
  }

  @ExceptionHandler({BaseException.class, Exception.class})
  public final ResponseEntity<Object> handleProcessExecutedException(Exception ex, WebRequest request) {
    String code = ExceptionCode.UNKNOWN_ERROR.getCode();
    String name = ExceptionCode.UNKNOWN_ERROR.getName();

    if (ex instanceof BaseException) {
      code = ((BaseException) ex).getCode();
      name = ((BaseException) ex).getName();
    }

    return handleGenericException(ex, code, name, request);
  }

  private ResponseEntity<Object> handleGenericException(Exception ex, String code, String name, WebRequest request) {
    String errorMsg =
        StringUtils.isBlank(ex.getMessage()) ? ExceptionUtils.getRootCause(ex).toString() : ex.getMessage();

    ExceptionErrorDto exceptionErrorDto = new ExceptionErrorDto(code, name, errorMsg);
    ApiResponseDto<ExceptionErrorDto> exceptionResultDto = new ApiResponseDto<>(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        ((ServletWebRequest) request).getRequest().getRequestURI(), exceptionErrorDto);

    printLog(ex, exceptionResultDto.toString());

    return handleExceptionInternal(ex, exceptionResultDto, new HttpHeaders(),
        HttpStatus.resolve(exceptionResultDto.getHttpStatusCode()), request);
  }
}
