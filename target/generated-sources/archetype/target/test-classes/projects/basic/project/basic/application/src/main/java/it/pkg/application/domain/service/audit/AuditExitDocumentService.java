package it.pkg.application.domain.service.audit;

import it.pkg.application.domain.model.domain.mongo.audit.AuditExitDocument;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditExitDocumentService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditExitDocument document);
}

