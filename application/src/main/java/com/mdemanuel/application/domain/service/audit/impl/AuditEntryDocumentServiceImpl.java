package com.mdemanuel.application.domain.service.audit.impl;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditEntryDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit.AuditEntryDocumentRepository;
import com.mdemanuel.application.domain.service.audit.AuditEntryDocumentService;
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
