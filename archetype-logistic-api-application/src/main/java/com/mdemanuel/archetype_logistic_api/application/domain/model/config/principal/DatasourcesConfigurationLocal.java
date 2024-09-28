package com.mdemanuel.archetype_logistic_api.application.domain.model.config.principal;

import com.mdemanuel.archetype_logistic_api.application.domain.model.config.DatasourcesPrimaryConfiguration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.mdemanuel.archetype_logistic_api.application.domain.model.domain",
        "com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository"},
    entityManagerFactoryRef = "primaryEntityManagerFactory", transactionManagerRef = "primaryTransactionManager")
@Profile("local")
public class DatasourcesConfigurationLocal extends DatasourcesPrimaryConfiguration {

  @Value("file:archetype-logistic-api-init/src/test/resources/schema.sql")
  private Resource sqlScriptSchema;

  @Value("file:archetype-logistic-api-init/src/test/resources/data.sql")
  private Resource sqlScriptData;

  @Value("${spring.datasource.datasource-initializer-enabled:false}")
  private Boolean dataSourceInitializerEnabled;

  public DatasourcesConfigurationLocal() {
    setPackages(new String[]{"com.mdemanuel.archetype_logistic_api.application.domain.model.domain",
        "com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository"});
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
    if (Boolean.TRUE.equals(dataSourceInitializerEnabled)) {
      DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
      dataSourceInitializer.setDataSource(dataSource);
      dataSourceInitializer.setDatabasePopulator(databasePopulator());
      return dataSourceInitializer;
    } else {
      return null;
    }
  }

  private DatabasePopulator databasePopulator() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(sqlScriptSchema);
    populator.addScript(sqlScriptData);
    // Separador, el valor predeterminado es ';'
    populator.setSeparator(";");
    return populator;
  }
}
