package it.pkg.application.util.aspect;

import it.pkg.application.domain.service.pojo.LogLevel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution {

  LogLevel level() default LogLevel.INFO;
}