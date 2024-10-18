package com.mdemanuel.init;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@ComponentScan({
    "com.mdemanuel.priadapter",
    "com.mdemanuel.secadapter",
    "com.mdemanuel.application",
    "com.mdemanuel.init"
})
public class StartRestApplication {

  private static final Logger logger = LoggerFactory.getLogger(StartRestApplication.class);

  public static void main(String[] args) {
    try {
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
      logger.info("****** Starting application ********");
      SpringApplication.run(StartRestApplication.class, args);
      logger.info("****** Application started OK *******");
    } catch (Exception e) {
      logger.error("ERROR : {}.", e.getMessage());
    }
  }
}
