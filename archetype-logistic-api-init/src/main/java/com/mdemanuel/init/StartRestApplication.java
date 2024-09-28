package com.mdemanuel.init;

import com.mdemanuel.init.config.Config;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({Config.class})
@ComponentScan({
    "com.mdemanuel.priadapter",
    "com.mdemanuel.secadapter",
    "com.mdemanuel.archetype_logistic_api"
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
