package com.mdemanuel.priadapter.api.config;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomJdbcAspect {

  @Around("execution(* com.p6spy.engine.event.JdbcEventListener.onAfterAddBatch(..))")
  public void aroundOnAfterAddBatch(ProceedingJoinPoint joinPoint)
      throws Throwable {
    aroundExecute(joinPoint);
  }

  @Around("execution(* com.p6spy.engine.event.JdbcEventListener.onAfterExecuteBatch(..))")
  public void aroundOnAfterExecuteBatch(ProceedingJoinPoint joinPoint)
      throws Throwable {
    aroundExecute(joinPoint);
  }

  @Around("execution(* com.p6spy.engine.event.JdbcEventListener.onAfterExecute(..))")
  public void aroundOnAfterExecute(ProceedingJoinPoint joinPoint)
      throws Throwable {
    aroundExecute(joinPoint);
  }

  @Around("execution(* com.p6spy.engine.event.JdbcEventListener.onAfterExecuteQuery(..))")
  public void aroundOnAfterExecuteQuery(ProceedingJoinPoint joinPoint)
      throws Throwable {
    aroundExecute(joinPoint);
  }

  @Around("execution(* com.p6spy.engine.event.JdbcEventListener.onAfterExecuteUpdate(..))")
  public void aroundOnAfterExecuteUpdate(ProceedingJoinPoint joinPoint)
      throws Throwable {
    aroundExecute(joinPoint);
  }

  public void aroundExecute(ProceedingJoinPoint joinPoint)
      throws Throwable {
    String method = joinPoint.getTarget().getClass() + " - " + joinPoint.getSignature();

    //log.debug("INICIO CustomJdbcAspect - {}", method);

    try {
      joinPoint.proceed();

      createMetric(joinPoint);
    } catch (Exception e) {
      throw e;
    } finally {
      //log.debug("FIN CustomJdbcAspect - {}", method);
    }
  }

  private <T extends Number> void createMetric(ProceedingJoinPoint joinPoint) {
    try {
      List<Tag> tags = new ArrayList<>();

      if (joinPoint.getArgs()[0] instanceof PreparedStatementInformation) {
        tags.add(Tag.of("poolName",
            ((HikariDataSource) ((PreparedStatementInformation) joinPoint.getArgs()[0]).getConnectionInformation()
                .getDataSource()).getPoolName()));
        tags.add(Tag.of("query", ((PreparedStatementInformation) joinPoint.getArgs()[0]).getSql()));
      } else if (joinPoint.getArgs()[0] instanceof StatementInformation) {
        tags.add(Tag.of("poolName",
            ((HikariDataSource) ((StatementInformation) joinPoint.getArgs()[0]).getConnectionInformation()
                .getDataSource()).getPoolName()));
        tags.add(Tag.of("query", (String) joinPoint.getArgs()[2]));
      }

      String metricName = "jdbc_query_execution_time";

      Metrics.timer(metricName, tags).record((long) joinPoint.getArgs()[1], TimeUnit.NANOSECONDS);
    } catch (Exception e) {
      log.error("ERROR CustomJdbcAspect.createMetric - {}", e.getLocalizedMessage());
    }
  }
}