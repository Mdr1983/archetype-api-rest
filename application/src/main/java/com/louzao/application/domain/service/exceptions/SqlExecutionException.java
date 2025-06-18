package com.louzao.application.domain.service.exceptions;

public class SqlExecutionException extends BaseException {

  private static final long serialVersionUID = -410054691333573001L;

  public SqlExecutionException(String msg) {
    super(ExceptionCode.SQL_BAD_EXECUTION.getCode(), ExceptionCode.SQL_BAD_EXECUTION.getName(), msg);
  }
}
