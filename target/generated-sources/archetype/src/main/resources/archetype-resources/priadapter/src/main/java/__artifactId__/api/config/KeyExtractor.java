#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.api.config;

import org.springframework.web.util.ContentCachingRequestWrapper;

@FunctionalInterface
public interface KeyExtractor {

  String extractKey(ContentCachingRequestWrapper reqWrapper, String body);
}

