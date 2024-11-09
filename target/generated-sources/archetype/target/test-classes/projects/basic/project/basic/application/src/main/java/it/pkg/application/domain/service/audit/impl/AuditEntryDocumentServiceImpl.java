package it.pkg.application.domain.service.audit.impl;

import it.pkg.application.domain.model.domain.mongo.audit.AuditEntryDocument;
import it.pkg.application.domain.ports.secondary.repository.mongo.audit.AuditEntryDocumentRepository;
import it.pkg.application.domain.service.audit.AuditEntryDocumentService;
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
