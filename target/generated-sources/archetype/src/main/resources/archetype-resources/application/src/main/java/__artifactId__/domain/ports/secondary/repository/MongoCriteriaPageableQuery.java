#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.secondary.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongoCriteriaPageableQuery<T> {

  @Autowired
  @Qualifier("mongoTemplate")
  private MongoTemplate mongoTemplate;

  public Page<T> find(DocumentMongoSpecification<T> documentMongoSpecification, Pageable pageable) {
    Query query = documentMongoSpecification.toQuery();

    List<T> result = mongoTemplate.find(query, documentMongoSpecification.getClazz());

    // Ejecuta otra consulta para obtener el total de documentos que cumplen los criterios (sin paginación)
    long total = mongoTemplate.count(query.skip(-1).limit(-1), documentMongoSpecification.getClazz());

    return new PageImpl<>(result, pageable, total);
  }
}
