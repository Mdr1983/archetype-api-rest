package com.mdemanuel.priadapter.api.adapter;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.response.controller.ExceptionErrorDto;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.AdapterException;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.ExceptionCode;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.ItemNotFoundException;
import java.lang.reflect.UndeclaredThrowableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  private static void logException(Exception ex, ApiResponseDto<ExceptionErrorDto> exceptionResultDto) {
    String logMsg = "Exception: " + exceptionResultDto.toString();

    if (ex instanceof ItemNotFoundException || ex instanceof JsonMappingException
        || ex instanceof HttpMessageNotReadableException
        || ex instanceof DuplicatedItemException) {
      log.warn(logMsg, ex);
    } else {
      log.error(logMsg, ex);
    }
  }

  @ExceptionHandler({UndeclaredThrowableException.class})
  public final ResponseEntity<Object> handleUndeclaredThrowableException(Exception ex, WebRequest request) {
    UndeclaredThrowableException uex = (UndeclaredThrowableException) ex;

    return handleProcessExecutedException((Exception) uex.getUndeclaredThrowable(), request);
  }

  @ExceptionHandler({RuntimeException.class, AdapterException.class,
      ItemNotFoundException.class, DuplicatedItemException.class, Exception.class})
  public final ResponseEntity<Object> handleProcessExecutedException(Exception ex, WebRequest request) {
    String code = ExceptionCode.UNKNOWN_ERROR.getCode();
    String name = ExceptionCode.UNKNOWN_ERROR.getName();
    HttpStatus status = null;

    if (ex instanceof ItemNotFoundException) {
      code = ((ItemNotFoundException) ex).getCode();
      name = ((ItemNotFoundException) ex).getName();
      status = HttpStatus.BAD_REQUEST;
    } else if (ex instanceof DuplicatedItemException) {
      code = ((DuplicatedItemException) ex).getCode();
      name = ((DuplicatedItemException) ex).getName();
    } else if (ex instanceof AdapterException) {
      code = ((AdapterException) ex).getCode();
      name = ((AdapterException) ex).getName();
    }

    return handleGenericException(ex, code, name, request, status);
  }

  private ResponseEntity<Object> handleGenericException(Exception ex, String code, String name,
      WebRequest request, HttpStatus status) {
    String errorMsg = ex instanceof InvalidDataAccessResourceUsageException
        ? ex.getCause().getCause().getMessage()
        : ex.getMessage();

    ExceptionErrorDto exceptionErrorDto = new ExceptionErrorDto(code, name, errorMsg);
    ApiResponseDto<ExceptionErrorDto> exceptionResultDto = new ApiResponseDto<>(
        status != null ? status.value() : HttpStatus.INTERNAL_SERVER_ERROR.value(),
        status != null ? status.getReasonPhrase() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        ((ServletWebRequest) request).getRequest().getRequestURI(), exceptionErrorDto);

    logException(ex, exceptionResultDto);

    return handleExceptionInternal(ex, exceptionResultDto, new HttpHeaders(),
        HttpStatus.resolve(exceptionResultDto.getHttpStatusCode()), request);
  }
}
