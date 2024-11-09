#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.audit;

import ${package}.${artifactId}.domain.model.domain.mongo.audit.AuditExitDocument;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditExitDocumentService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditExitDocument document);
}

