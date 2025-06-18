package com.louzao.init;

import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.louzao.priadapter",
    "com.louzao.secadapter",
    "com.louzao.application",
    "com.louzao.init"
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
