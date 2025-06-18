package com.louzao.application.config;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.louzao.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.louzao.application.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto;
import com.louzao.application.domain.service.util.SearchCriteriaDtoDeserializer;
import com.louzao.application.domain.service.util.SearchCriteriaDtoSerializer;
import com.louzao.application.domain.service.util.SearchCriteriaGroupDtoDeserializer;
import com.louzao.application.domain.service.util.SearchCriteriaGroupDtoSerializer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@EnableAutoConfiguration
public class SpringConfig {

  @Bean
  @Primary
  public ObjectMapper primaryObjectMapper() {
    SimpleModule moduleSearchCriteriaDto = new SimpleModule("SearchCriteriaDtoModule");
    moduleSearchCriteriaDto.addSerializer(SearchCriteriaDto.class, new SearchCriteriaDtoSerializer());
    moduleSearchCriteriaDto.addDeserializer(SearchCriteriaDto.class, new SearchCriteriaDtoDeserializer());
    moduleSearchCriteriaDto.addSerializer(SearchCriteriaGroupDto.class, new SearchCriteriaGroupDtoSerializer());
    moduleSearchCriteriaDto.addDeserializer(SearchCriteriaGroupDto.class, new SearchCriteriaGroupDtoDeserializer());

    return (new ObjectMapper())
        .registerModule(new ParameterNamesModule(Mode.DEFAULT))
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .registerModule(moduleSearchCriteriaDto)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
