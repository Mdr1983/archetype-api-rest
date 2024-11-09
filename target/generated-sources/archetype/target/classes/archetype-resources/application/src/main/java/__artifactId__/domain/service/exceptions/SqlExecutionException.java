#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.exceptions;

import ${package}.${artifactId}.domain.model.exception.BaseException;
import ${package}.${artifactId}.domain.model.exception.ExceptionCode;

public class SqlExecutionException extends BaseException {

  private static final long serialVersionUID = -410054691333573001L;

  public SqlExecutionException(String msg) {
    super(ExceptionCode.SQL_BAD_EXECUTION.getCode(), ExceptionCode.SQL_BAD_EXECUTION.getName(), msg);
  }
}
