package com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions;

import com.mdemanuel.archetype_logistic_api.application.domain.model.exception.BaseException;

public class BadFormatException extends BaseException {

  private static final long serialVersionUID = 3476292942226599490L;

  private static ExceptionCode exceptionCode = ExceptionCode.BAD_FORMAT;

  public BadFormatException(String message) {
    super(exceptionCode.getCode(), exceptionCode.getName(), message);
  }

}
