package com.louzao.application.domain.ports.secondary.repository.mssql.audit;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditExitEntity;

public interface AuditExitRepository {

  AuditExitEntity save(AuditExitEntity entity);
}
