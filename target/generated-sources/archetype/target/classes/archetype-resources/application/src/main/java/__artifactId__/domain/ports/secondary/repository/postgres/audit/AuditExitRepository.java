#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.secondary.repository.postgres.audit;

import ${package}.${artifactId}.domain.model.domain.postgres.audit.AuditExitEntity;

public interface AuditExitRepository {

  AuditExitEntity save(AuditExitEntity entity);
}
