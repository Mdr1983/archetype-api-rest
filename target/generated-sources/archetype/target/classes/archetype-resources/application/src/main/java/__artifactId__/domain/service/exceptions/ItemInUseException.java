#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.exceptions;

import ${package}.${artifactId}.domain.model.exception.BaseException;

public class ItemInUseException extends BaseException {

  private static final long serialVersionUID = 4143071234441295843L;

  private static ExceptionCode exceptionCode = ExceptionCode.ITEM_IN_USE;

  public ItemInUseException(String item, String value) {
    super(exceptionCode.getCode(), exceptionCode.getName(),
        String.format(
            "Item in use %s: %s", item, value));
  }

  public ItemInUseException(String attribute, String item, String value) {
    super(exceptionCode.getCode(), exceptionCode.getName(),
        String.format(
            "Item in use %s for %s: %s", attribute, item, value));
  }

}
