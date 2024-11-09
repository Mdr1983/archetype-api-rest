#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.exceptions;

import ${package}.${artifactId}.domain.model.exception.BaseException;

public class DuplicatedItemException extends BaseException {

  private static final long serialVersionUID = 4143070404441295843L;

  private static ExceptionCode exceptionCode = ExceptionCode.DUPLICATED_ITEM;

  public DuplicatedItemException(String item, String value) {
    super(exceptionCode.getCode(), exceptionCode.getName(),
        String.format(
            "Duplicated %s: %s", item, value));
  }

  public DuplicatedItemException(String attribute, String item, String value) {
    super(exceptionCode.getCode(), exceptionCode.getName(),
        String.format(
            "Duplicated %s for %s: %s", attribute, item, value));
  }

}
