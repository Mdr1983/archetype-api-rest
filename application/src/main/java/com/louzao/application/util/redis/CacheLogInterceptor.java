package com.louzao.application.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheInterceptor;

@Slf4j
public class CacheLogInterceptor extends CacheInterceptor {

  @Value("spring.cache.redis.logger-active:false")
  private boolean redisLoggerActive;

  @Override
  public Object invoke(MethodInvocation invocation)
      throws Throwable {
    Object result = super.invoke(invocation);
    if (this.redisLoggerActive) {
      logCacheInvoke(invocation, result);
    }

    return result;
  }

  private void logCacheInvoke(MethodInvocation methodInvocation, Object item) {
    if (methodInvocation.getMethod().getDeclaredAnnotation(Cacheable.class) != null) {
      log.debug("Cache Invoke Method: {}, key: {}, value: {}", new Object[]{methodInvocation.getMethod().getName(),
          ((Cacheable) methodInvocation.getMethod().getDeclaredAnnotation(Cacheable.class)).key(),
          item != null ? item.toString() : ""});
    }
  }
}
