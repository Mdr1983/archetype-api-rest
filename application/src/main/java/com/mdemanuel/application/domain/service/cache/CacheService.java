package com.mdemanuel.application.domain.service.cache;

import org.springframework.stereotype.Service;

@Service
public interface CacheService {

  void evictAll();

}