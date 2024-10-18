package com.mdemanuel.application.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogExecutionAspect {

  @Around("@annotation(LogExecution)")
  public Object logExecution(ProceedingJoinPoint joinPoint, LogExecution logExecution)
      throws Throwable {
    log(logExecution, "INIT - " + joinPoint.getSignature());

    try {
      return joinPoint.proceed();
    } finally {
      log(logExecution, "END - " + joinPoint.getSignature());
    }
  }

  private void log(LogExecution logExecution, String message) {
    switch (logExecution.level()) {
      case TRACE:
        log.trace(message);
        break;
      case DEBUG:
        log.debug(message);
        break;
      case INFO:
        log.info(message);
        break;
      case WARN:
        log.warn(message);
        break;
      case ERROR:
        log.error(message);
        break;
      default:
        log.info(message);
        break;
    }
  }
}