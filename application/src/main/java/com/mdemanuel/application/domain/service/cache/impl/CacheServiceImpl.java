package com.mdemanuel.application.domain.service.cache.impl;

import com.mdemanuel.application.domain.service.cache.CacheService;
import com.mdemanuel.application.util.aspect.LogExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

  @Autowired
  private CacheManager cacheManager;

  @Override
  @LogExecution
  public void evictAll() {
    cacheManager.getCacheNames().stream().forEach(n -> cacheManager.getCache(n).clear());
  }

  public void evict(String cacheName, String key) {
    cacheManager.getCache(cacheName).evict(key);
  }
}
