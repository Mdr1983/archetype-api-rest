package com.mdemanuel.application.domain.service.exceptions;

import com.mdemanuel.application.domain.model.exception.BaseException;
import com.mdemanuel.application.domain.model.exception.ExceptionCode;

public class SqlExecutionException extends BaseException {

  private static final long serialVersionUID = -410054691333573001L;

  public SqlExecutionException(String msg) {
    super(ExceptionCode.SQL_BAD_EXECUTION.getCode(), ExceptionCode.SQL_BAD_EXECUTION.getName(), msg);
  }
}
