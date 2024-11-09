package it.pkg.application.domain.ports.secondary.repository.mongo.audit;

import it.pkg.application.domain.model.domain.mongo.audit.AuditExitDocument;

public interface AuditExitDocumentRepository {

  AuditExitDocument save(AuditExitDocument entity);
}
