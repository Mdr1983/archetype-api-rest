package com.mdemanuel.application.domain.service.exceptions;

import com.mdemanuel.application.domain.model.exception.BaseException;
import com.mdemanuel.application.domain.model.exception.ExceptionCode;

public class AdapterException extends BaseException {

  private static final long serialVersionUID = -9187152576089172509L;

  public AdapterException() {
    super(ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getCode(), ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getName());
  }

  public AdapterException(String message) {
    super(ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getCode(), ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getName(),
        message);
  }

  public AdapterException(String message, Throwable cause) {
    super(ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getCode(), ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getName(), message,
        cause);
  }

  public AdapterException(Throwable cause) {
    super(ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getCode(), ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getName(), cause);
  }

  public AdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getCode(), ExceptionCode.GENERIC_ADAPTER_EXCEPTION.getName(), message,
        cause, enableSuppression, writableStackTrace);
  }
}