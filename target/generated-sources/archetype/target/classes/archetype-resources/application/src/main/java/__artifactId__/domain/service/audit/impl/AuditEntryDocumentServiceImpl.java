#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.audit.impl;

import ${package}.${artifactId}.domain.model.domain.mongo.audit.AuditEntryDocument;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.audit.AuditEntryDocumentRepository;
import ${package}.${artifactId}.domain.service.audit.AuditEntryDocumentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditEntryDocumentServiceImpl implements AuditEntryDocumentService {

  @Autowired
  private AuditEntryDocumentRepository auditEntryDocumentRepository;

  @Override
  @SneakyThrows
  public void save(AuditEntryDocument document) {
    auditEntryDocumentRepository.save(document);
  }
}
