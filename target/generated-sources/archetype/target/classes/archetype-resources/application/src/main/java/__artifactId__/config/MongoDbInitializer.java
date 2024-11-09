#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.config;

import ${package}.${artifactId}.domain.model.util.DbDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component

public class MongoDbInitializer implements CommandLineRunner {

  @Autowired
  @Qualifier("mongoTemplate")
  private MongoTemplate mongoTemplate;

  @Override
  public void run(String... args)
      throws Exception {
    mongoTemplate.createCollection(DbDocuments.AUDIT_ENTRY);
    mongoTemplate.createCollection(DbDocuments.AUDIT_EXIT);
    mongoTemplate.createCollection(DbDocuments.CATEGORY);
    mongoTemplate.createCollection(DbDocuments.CATEGORY_GENERIC);
    mongoTemplate.createCollection(DbDocuments.PURCHASE_ORDER);
    mongoTemplate.createCollection(DbDocuments.PURCHASE_ORDER_GENERIC);

    mongoTemplate.indexOps(DbDocuments.AUDIT_ENTRY).ensureIndex(new Index("trace_id", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_ENTRY).ensureIndex(new Index("url", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_ENTRY).ensureIndex(new Index("http_status", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_ENTRY).ensureIndex(new Index("key_value", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_ENTRY).ensureIndex(new Index("created_at", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_ENTRY).ensureIndex(new Index("updated_at", Direction.ASC));

    mongoTemplate.indexOps(DbDocuments.AUDIT_EXIT).ensureIndex(new Index("trace_id", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_EXIT).ensureIndex(new Index("url", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_EXIT).ensureIndex(new Index("http_status", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_EXIT).ensureIndex(new Index("created_at", Direction.ASC));
    mongoTemplate.indexOps(DbDocuments.AUDIT_EXIT).ensureIndex(new Index("updated_at", Direction.ASC));

    mongoTemplate.indexOps(DbDocuments.CATEGORY).ensureIndex(new Index("code", Direction.ASC).unique());
    mongoTemplate.indexOps(DbDocuments.CATEGORY_GENERIC).ensureIndex(new Index("data.code", Direction.ASC).unique());

    mongoTemplate.indexOps(DbDocuments.PURCHASE_ORDER).ensureIndex(new Index("code", Direction.ASC).unique());
    mongoTemplate.indexOps(DbDocuments.PURCHASE_ORDER_GENERIC)
        .ensureIndex(new Index("data.code", Direction.ASC).unique());
  }
}
