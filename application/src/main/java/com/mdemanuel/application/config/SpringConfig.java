package com.mdemanuel.application.config;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.service.util.SearchCriteriaDtoDeserializer;
import com.mdemanuel.application.domain.service.util.SearchCriteriaDtoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class SpringConfig {

  @Bean
  @Primary
  public ObjectMapper primaryObjectMapper() {
    SimpleModule module = new SimpleModule("SearchCriteriaDtoModule");
    module.addSerializer(SearchCriteriaDto.class, new SearchCriteriaDtoSerializer());
    module.addDeserializer(SearchCriteriaDto.class, new SearchCriteriaDtoDeserializer());

    return (new ObjectMapper())
        .registerModule(new ParameterNamesModule(Mode.DEFAULT))
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .registerModule(module)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
