package com.louzao.application.domain.service.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExternalServiceException extends Exception {

  public ExternalServiceException(String message) {
    super(message);
  }

  public ExternalServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExternalServiceException(Throwable cause) {
    super(cause);
  }

  public ExternalServiceException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
