package com.mdemanuel.archetype_logistic_api.application.domain.model.exception;

public class EntityMappingException extends BaseException {

  private static final long serialVersionUID = -9151437198023413468L;

  public EntityMappingException() {
  }

  public EntityMappingException(String message) {
    super(message);
  }

  public EntityMappingException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityMappingException(Throwable cause) {
    super(cause);
  }

  public EntityMappingException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
