package com.louzao.application.config;

import com.louzao.application.util.auditlogging.AuditExitLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Autowired
  private AuditExitLoggingInterceptor auditExitLoggingInterceptor;

  @Bean
  public RestClient restClient() {
    return RestClient.builder().requestInterceptor(auditExitLoggingInterceptor).build();
  }
}
