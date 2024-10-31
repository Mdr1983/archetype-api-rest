package com.mdemanuel.application.domain.model.config.secondary;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(
    basePackages = {
        "com.mdemanuel.application.domain.model.domain.mongo",
        "com.mdemanuel.application.domain.ports.secondary.repository.mongo"},
    mongoTemplateRef = "mongoTemplate"
)
public class DatasourcesSecondaryConfiguration {

  @Value("${spring.data.mongodb.uri}")
  private String uri;

  @Value("${spring.data.mongodb.database}")
  private String database;

  @Value("${spring.data.mongodb.username}")
  private String username;

  @Value("${spring.data.mongodb.password}")
  private String password;

  @Value("${spring.data.mongodb.authentication-database}")
  private String authenticationDatabase;

  @Primary
  @Bean
  public MongoClient mongoClient() {
    MongoCredential credential = MongoCredential.createCredential(username, authenticationDatabase,
        password.toCharArray());

    MongoClientSettings settings = MongoClientSettings.builder()
        .credential(credential)
        .applyConnectionString(new com.mongodb.ConnectionString(uri))
        .build();

    return MongoClients.create(settings);
  }

  @Primary
  @Bean
  public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
    return new SimpleMongoClientDatabaseFactory(mongoClient, database);
  }

  @Primary
  @Bean(name = "mongoTemplate")
  public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
    return new MongoTemplate(mongoDatabaseFactory);
  }

  //  @Primary
  //  @Bean(name = {"secondaryTransactionManager"})
  //  public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
  //    return new MongoTransactionManager(mongoDatabaseFactory);
  //  }
}
