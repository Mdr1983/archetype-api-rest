package it.pkg.application.domain.service.cache;

import org.springframework.stereotype.Service;

@Service
public interface CacheService {

  void evictAll();

  void evict(String cacheName, String key);
}
