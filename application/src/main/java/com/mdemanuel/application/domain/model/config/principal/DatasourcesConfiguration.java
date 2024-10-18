package com.mdemanuel.application.domain.model.config.principal;

import com.mdemanuel.application.domain.model.config.DatasourcesPrimaryConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.mdemanuel.application.domain.model.domain",
        "com.mdemanuel.application.domain.ports.secondary.repository"},
    entityManagerFactoryRef = "primaryEntityManagerFactory", transactionManagerRef = "primaryTransactionManager")
@Profile("!h2")
public class DatasourcesConfiguration extends DatasourcesPrimaryConfiguration {

  public DatasourcesConfiguration() {
    setPackages(new String[]{"com.mdemanuel.application.domain.model.domain",
        "com.mdemanuel.application.domain.ports.secondary.repository"});
  }
}
