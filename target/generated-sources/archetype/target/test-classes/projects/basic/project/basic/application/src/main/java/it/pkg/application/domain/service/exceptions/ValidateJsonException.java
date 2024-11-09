package it.pkg.application.domain.service.exceptions;

import it.pkg.application.domain.model.exception.BaseException;
import it.pkg.application.domain.model.exception.ExceptionCode;

public class ValidateJsonException extends BaseException {

  private static final long serialVersionUID = -9123452576089172509L;

  public ValidateJsonException() {
    super(ExceptionCode.VALIDATE_JSON_EXCEPTION.getCode(), ExceptionCode.VALIDATE_JSON_EXCEPTION.getName());
  }

  public ValidateJsonException(String message) {
    super(ExceptionCode.VALIDATE_JSON_EXCEPTION.getCode(), ExceptionCode.VALIDATE_JSON_EXCEPTION.getName(),
        message);
  }

  public ValidateJsonException(String message, Throwable cause) {
    super(ExceptionCode.VALIDATE_JSON_EXCEPTION.getCode(), ExceptionCode.VALIDATE_JSON_EXCEPTION.getName(), message,
        cause);
  }

  public ValidateJsonException(Throwable cause) {
    super(ExceptionCode.VALIDATE_JSON_EXCEPTION.getCode(), ExceptionCode.VALIDATE_JSON_EXCEPTION.getName(), cause);
  }

  public ValidateJsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(ExceptionCode.VALIDATE_JSON_EXCEPTION.getCode(), ExceptionCode.VALIDATE_JSON_EXCEPTION.getName(), message,
        cause, enableSuppression, writableStackTrace);
  }
}