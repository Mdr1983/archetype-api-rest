package com.louzao.application.domain.ports.secondary.repository.mssql.audit;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditEntryEntity;

public interface AuditEntryRepository {

  AuditEntryEntity save(AuditEntryEntity entity);
}
