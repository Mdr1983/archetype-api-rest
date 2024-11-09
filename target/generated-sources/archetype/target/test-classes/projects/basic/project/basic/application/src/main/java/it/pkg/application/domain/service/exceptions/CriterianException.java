package it.pkg.application.domain.service.exceptions;

import it.pkg.application.domain.model.exception.BaseException;
import it.pkg.application.domain.model.exception.ExceptionCode;

public class CriterianException extends BaseException {

  private static final long serialVersionUID = -9112152576089172509L;

  public CriterianException() {
    super(ExceptionCode.CRITERIAN_EXCEPTION.getCode(), ExceptionCode.CRITERIAN_EXCEPTION.getName());
  }

  public CriterianException(String message) {
    super(ExceptionCode.CRITERIAN_EXCEPTION.getCode(), ExceptionCode.CRITERIAN_EXCEPTION.getName(),
        message);
  }

  public CriterianException(String message, Throwable cause) {
    super(ExceptionCode.CRITERIAN_EXCEPTION.getCode(), ExceptionCode.CRITERIAN_EXCEPTION.getName(), message,
        cause);
  }

  public CriterianException(Throwable cause) {
    super(ExceptionCode.CRITERIAN_EXCEPTION.getCode(), ExceptionCode.CRITERIAN_EXCEPTION.getName(), cause);
  }

  public CriterianException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(ExceptionCode.CRITERIAN_EXCEPTION.getCode(), ExceptionCode.CRITERIAN_EXCEPTION.getName(), message,
        cause, enableSuppression, writableStackTrace);
  }
}