package it.pkg.application.domain.ports.secondary.repository.postgres.audit;

import it.pkg.application.domain.model.domain.postgres.audit.AuditExitEntity;

public interface AuditExitRepository {

  AuditExitEntity save(AuditExitEntity entity);
}
