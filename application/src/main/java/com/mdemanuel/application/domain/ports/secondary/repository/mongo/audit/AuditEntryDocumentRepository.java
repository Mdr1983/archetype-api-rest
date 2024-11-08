package com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditEntryDocument;

public interface AuditEntryDocumentRepository {

  AuditEntryDocument save(AuditEntryDocument entity);
}
