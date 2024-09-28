package com.mdemanuel.archetype_logistic_api.application.domain.service.cache;

import org.springframework.stereotype.Service;

@Service
public interface CacheService {

  void evictAll();

}
