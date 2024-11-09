package it.pkg.application.domain.ports.secondary.repository.postgres.audit;

import it.pkg.application.domain.model.domain.postgres.audit.AuditEntryEntity;

public interface AuditEntryRepository {

  AuditEntryEntity save(AuditEntryEntity entity);
}
