package com.mdemanuel.application.domain.ports.secondary.repository.audit;

import com.mdemanuel.application.domain.model.domain.audit.AuditEntryEntity;
import com.mdemanuel.application.domain.model.exception.SqlBadExecution;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface AuditEntryRepository {

  int save(AuditEntryEntity auditEntryEntity)
      throws SqlBadExecution;
}
