#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.config.primary;

import javax.sql.DataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "${package}.${artifactId}.domain.model.domain.postgres",
        "${package}.${artifactId}.domain.ports.secondary.repository.postgres"},
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager")
@Data
public class DatasourcesPrimaryConfiguration {

  private String[] packages;

  @Value("file:init/src/test/resources/schema.sql")
  private Resource sqlScriptSchema;

  @Value("file:init/src/test/resources/data.sql")
  private Resource sqlScriptData;

  @Value("${symbol_dollar}{spring.datasource.datasource-initializer-enabled:false}")
  private Boolean dataSourceInitializerEnabled;

  public DatasourcesPrimaryConfiguration() {
    setPackages(new String[]{
        "${package}.${artifactId}.domain.model.domain.postgres",
        "${package}.${artifactId}.domain.ports.secondary.repository.postgres"});
  }

  @Primary
  @Bean(name = {"primaryEntityManagerFactory"})
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(this.dataSource()).packages(this.packages).build();
  }

  @Primary
  @Bean(name = {"primaryTransactionManager"})
  public PlatformTransactionManager transactionManager(
      @Qualifier("primaryEntityManagerFactory") final LocalContainerEntityManagerFactoryBean emFactory) {
    return new JpaTransactionManager(emFactory.getObject());
  }

  @Primary
  @Bean(name = {"dataSource"})
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = {"jdbcTemplate"})
  public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Primary
  @Bean(name = {"namedParameterJdbcTemplate"})
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") final DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
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
