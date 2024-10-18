package com.mdemanuel.application.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringBeanUtil implements ApplicationContextAware {

  private static final SpringBeanUtil INSTANCE = new SpringBeanUtil();
  private ApplicationContext applicationContext = null;

  public SpringBeanUtil() {
  }

  @Bean
  public static SpringBeanUtil getInstance() {
    return INSTANCE;
  }

  public void setApplicationContext(ApplicationContext valueApplicationContext) {
    log.info("setApplicationContext : {}", valueApplicationContext);
    this.applicationContext = valueApplicationContext;
  }

  public <T> T getBean(Class<T> clazz) {
    log.debug("Buscando clazz {}", clazz.toString());

    return this.applicationContext.getBean(clazz);
  }

  public Object getBean(String name) {
    return this.applicationContext.getBean(name);
  }

  public <T> T getBean(String name, Class<T> clazz) {
    return this.applicationContext.getBean(name, clazz);
  }
}
