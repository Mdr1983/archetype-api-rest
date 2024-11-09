package it.pkg.application.domain.service.audit;

import it.pkg.application.domain.model.domain.mongo.audit.AuditEntryDocument;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditEntryDocumentService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditEntryDocument document);
}
