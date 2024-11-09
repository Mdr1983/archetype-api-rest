#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.exception;

import lombok.Generated;

public class BaseException extends Exception {

  private static final long serialVersionUID = 1118332401123376680L;

  protected String code;
  protected String name;

  public BaseException() {
    this.initializeCodeAndName(ExceptionCode.UNKNOWN_ERROR.getCode(), ExceptionCode.UNKNOWN_ERROR.getName());
  }

  public BaseException(String code, String name) {
    this.initializeCodeAndName(code, name);
  }

  public BaseException(String message) {
    super(message);
    this.initializeCodeAndName(ExceptionCode.UNKNOWN_ERROR.getCode(), ExceptionCode.UNKNOWN_ERROR.getName());
  }

  public BaseException(String code, String name, String message) {
    super(message);
    this.initializeCodeAndName(code, name);
  }

  public BaseException(Throwable cause) {
    super(cause);
    this.initializeCodeAndName(ExceptionCode.UNKNOWN_ERROR.getCode(), ExceptionCode.UNKNOWN_ERROR.getName());
  }

  public BaseException(String code, String name, Throwable cause) {
    super(cause);
    this.initializeCodeAndName(code, name);
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
    this.initializeCodeAndName(ExceptionCode.UNKNOWN_ERROR.getCode(), ExceptionCode.UNKNOWN_ERROR.getName());
  }

  public BaseException(String code, String name, String message, Throwable cause) {
    super(message, cause);
    this.initializeCodeAndName(code, name);
  }

  public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.initializeCodeAndName(ExceptionCode.UNKNOWN_ERROR.getCode(), ExceptionCode.UNKNOWN_ERROR.getName());
  }

  public BaseException(String code, String name, String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.initializeCodeAndName(code, name);
  }

  protected void initializeCodeAndName(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Generated
  public String getCode() {
    return this.code;
  }

  @Generated
  public String getName() {
    return this.name;
  }
}
