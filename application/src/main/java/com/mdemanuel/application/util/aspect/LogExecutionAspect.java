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
  public Object logExecution(ProceedingJoinPoint joinPoint)
      throws Throwable {
    log.info("INIT - {}", joinPoint.getSignature());

    try {
      return joinPoint.proceed();
    } finally {
      log.info("END - {}", joinPoint.getSignature());
    }
  }
}