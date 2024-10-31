package com.mdemanuel.application.domain.service.audit;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditExitDocument;
import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditExitEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditExitDocumentService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditExitDocument document);
}

