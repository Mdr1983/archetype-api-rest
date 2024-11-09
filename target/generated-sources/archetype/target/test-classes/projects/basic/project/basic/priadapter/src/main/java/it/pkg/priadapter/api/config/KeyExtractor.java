package it.pkg.priadapter.api.config;

import org.springframework.web.util.ContentCachingRequestWrapper;

@FunctionalInterface
public interface KeyExtractor {

  String extractKey(ContentCachingRequestWrapper reqWrapper, String body);
}

