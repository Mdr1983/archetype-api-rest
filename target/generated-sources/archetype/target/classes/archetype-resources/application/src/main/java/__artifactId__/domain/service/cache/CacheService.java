#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.cache;

import org.springframework.stereotype.Service;

@Service
public interface CacheService {

  void evictAll();

  void evict(String cacheName, String key);
}
