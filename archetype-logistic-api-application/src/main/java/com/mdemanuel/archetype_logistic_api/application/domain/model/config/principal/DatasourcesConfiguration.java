package com.mdemanuel.archetype_logistic_api.application.domain.model.config.principal;

import com.mdemanuel.archetype_logistic_api.application.domain.model.config.DatasourcesPrimaryConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.mdemanuel.archetype_logistic_api.application.domain.model.domain",
        "com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository"},
    entityManagerFactoryRef = "primaryEntityManagerFactory", transactionManagerRef = "primaryTransactionManager")
@Profile("!local")
public class DatasourcesConfiguration extends DatasourcesPrimaryConfiguration {

  public DatasourcesConfiguration() {
    setPackages(new String[]{"com.mdemanuel.archetype_logistic_api.application.domain.model.domain",
        "com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository"});
  }
}
