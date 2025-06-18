package com.louzao.application.domain.service.audit;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditEntryEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditEntryService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditEntryEntity entity);
}
