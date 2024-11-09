#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.exception;

import lombok.Generated;

public class BadParamsException extends BaseException {

  private static final long serialVersionUID = 3970812342022430499L;

  public BadParamsException() {
    super(ExceptionCode.BAD_PARAMS.getCode(), ExceptionCode.BAD_PARAMS.getName());
  }

  public String getCode() {
    return ExceptionCode.BAD_PARAMS.getCode();
  }

  public String getName() {
    return ExceptionCode.BAD_PARAMS.getName();
  }

  @Generated
  public BadParamsException(final String message) {
    this(message, (Throwable) null);
  }

  @Generated
  public BadParamsException(final Throwable cause) {
    this(cause != null ? cause.getMessage() : null, cause);
  }

  @Generated
  public BadParamsException(final String message, final Throwable cause) {
    super(message);
    if (cause != null) {
      super.initCause(cause);
    }
  }
}