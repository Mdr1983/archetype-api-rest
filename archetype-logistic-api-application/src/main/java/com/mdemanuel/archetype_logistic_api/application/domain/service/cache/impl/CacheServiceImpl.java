package com.mdemanuel.archetype_logistic_api.application.domain.service.cache.impl;

import com.mdemanuel.archetype_logistic_api.application.domain.service.cache.CacheService;
import com.mdemanuel.archetype_logistic_api.application.util.aspect.LogExecution;
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
}
