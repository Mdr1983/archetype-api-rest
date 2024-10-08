package com.mdemanuel.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Slf4j
@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig extends CachingConfigurerSupport {

  private ObjectMapper redisCacheObjectMapper = new ObjectMapper();

  @Value("${spring.cache.redis.use-key-prefix:false}")
  private boolean useKeyPrefix;
  @Value("${spring.cache.redis.use-prefix}")
  private String usePrefix;

  @Autowired
  private RedisProperties redisProperties;

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    log.info("Redis Configuration - redisTemplate.");

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new CustomKeyPrefixSerializer(getCacheApplicationKeyPrefix()));
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer(this.redisCacheObjectMapper));
    template.afterPropertiesSet();

    return template;
  }

  @Bean
  @Primary
  public CacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory) {
    log.info("Redis Configuration - cacheManager.");

    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap();

    /*
    if (Objects.nonNull(this.cacheConfigurationProperties.getCachesTtl())) {
      Iterator var4 = this.cacheConfigurationProperties.getCachesTtl().entrySet().iterator();

      while (var4.hasNext()) {
        Map.Entry<String, Long> cacheNameAndTimeout = (Map.Entry) var4.next();
        log.info("Creando la Configuración de la Caché, cacheName: {}, timeoutInSeconds: {}",
            cacheNameAndTimeout.getKey(), cacheNameAndTimeout.getValue());

        cacheConfigurations.put((String) cacheNameAndTimeout.getKey(),
            this.createApplicationCacheConfiguration((Long) cacheNameAndTimeout.getValue(), cacheKeyPrefix));
      }
    }*/

    return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(getCacheKeyPrefix()))
        .withInitialCacheConfigurations(cacheConfigurations).build();
  }

  private String getCacheApplicationKeyPrefix() {
    return usePrefix.trim() + ":";
  }

  private CacheKeyPrefix getCacheKeyPrefix() {
    return (cacheName) -> {
      return getCacheApplicationKeyPrefix() + cacheName + ":";
    };
  }
}
