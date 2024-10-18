package com.mdemanuel.application.domain.model.config;

import javax.sql.DataSource;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@NoArgsConstructor
public class DatasourcesPrimaryConfiguration extends BaseDatasourcesConfiguration {

  @Primary
  @Bean(
      name = {"primaryEntityManagerFactory"}
  )
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    return super.entityManagerFactory(builder);
  }

  @Primary
  @Bean(
      name = {"primaryTransactionManager"}
  )
  public PlatformTransactionManager transactionManager(
      @Qualifier("primaryEntityManagerFactory") final LocalContainerEntityManagerFactoryBean emFactory) {
    return super.transactionManager(emFactory);
  }

  @Primary
  @Bean(
      name = {"dataSource"}
  )
  @ConfigurationProperties(
      prefix = "spring.datasource"
  )
  public DataSource dataSource() {
    return super.dataSource();
  }

  @Primary
  @Bean(
      name = {"jdbcTemplate"}
  )
  public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") final DataSource dataSource) {
    return super.jdbcTemplate(dataSource);
  }
}
