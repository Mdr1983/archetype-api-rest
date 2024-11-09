#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.secondary.repository.mongo.audit;

import ${package}.${artifactId}.domain.model.domain.mongo.audit.AuditEntryDocument;

public interface AuditEntryDocumentRepository {

  AuditEntryDocument save(AuditEntryDocument entity);
}
