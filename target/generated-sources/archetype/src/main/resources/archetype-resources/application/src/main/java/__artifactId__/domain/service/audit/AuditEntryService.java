#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.audit;

import ${package}.${artifactId}.domain.model.domain.postgres.audit.AuditEntryEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditEntryService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditEntryEntity entity);
}
