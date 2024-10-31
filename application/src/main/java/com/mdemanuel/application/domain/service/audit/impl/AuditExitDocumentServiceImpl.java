package com.mdemanuel.application.domain.service.audit.impl;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditExitDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit.AuditExitDocumentRepository;
import com.mdemanuel.application.domain.service.audit.AuditExitDocumentService;
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
