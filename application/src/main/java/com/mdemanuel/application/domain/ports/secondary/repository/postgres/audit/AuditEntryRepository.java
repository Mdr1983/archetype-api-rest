package com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditEntryEntity;

public interface AuditEntryRepository {

  AuditEntryEntity save(AuditEntryEntity entity);
}
