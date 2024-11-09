#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.audit.impl;

import ${package}.${artifactId}.domain.model.domain.mongo.audit.AuditExitDocument;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.audit.AuditExitDocumentRepository;
import ${package}.${artifactId}.domain.service.audit.AuditExitDocumentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditExitDocumentServiceImpl implements AuditExitDocumentService {

  @Autowired
  private AuditExitDocumentRepository auditExitDocumentRepository;

  @Override
  @SneakyThrows
  public void save(AuditExitDocument auditExitDocument) {
    auditExitDocumentRepository.save(auditExitDocument);
  }
}
