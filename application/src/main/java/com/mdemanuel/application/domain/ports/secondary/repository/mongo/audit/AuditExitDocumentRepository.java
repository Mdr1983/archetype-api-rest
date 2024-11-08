package com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditExitDocument;

public interface AuditExitDocumentRepository {

  AuditExitDocument save(AuditExitDocument entity);
}
