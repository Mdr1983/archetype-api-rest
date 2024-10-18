package com.mdemanuel.application.domain.model.config;

import javax.sql.DataSource;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Data
@Slf4j
@NoArgsConstructor
public class BaseDatasourcesConfiguration {

  private String[] packages;

  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(this.dataSource()).packages(this.packages).build();
  }

  public PlatformTransactionManager transactionManager(
      final LocalContainerEntityManagerFactoryBean memberEntityManagerFactory) {
    return new JpaTransactionManager(memberEntityManagerFactory.getObject());
  }

  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
