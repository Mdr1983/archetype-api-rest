package com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditExitEntity;

public interface AuditExitRepository {

  AuditExitEntity save(AuditExitEntity entity);
}
