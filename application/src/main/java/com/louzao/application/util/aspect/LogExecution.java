package com.louzao.application.util.aspect;

import com.louzao.application.domain.service.pojo.LogLevel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution {

  LogLevel level() default LogLevel.INFO;
}
