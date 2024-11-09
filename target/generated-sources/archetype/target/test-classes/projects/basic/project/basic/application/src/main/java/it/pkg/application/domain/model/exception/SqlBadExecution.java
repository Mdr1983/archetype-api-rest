package it.pkg.application.domain.model.exception;

public class SqlBadExecution extends BaseException {

  private static final long serialVersionUID = -408054622133573001L;

  public SqlBadExecution(String msg) {
    super(ExceptionCode.SQL_BAD_EXECUTION.getCode(), ExceptionCode.SQL_BAD_EXECUTION.getName(), msg);
  }
}
