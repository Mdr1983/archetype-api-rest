package it.pkg.application.domain.service.audit;

import it.pkg.application.domain.model.domain.postgres.audit.AuditEntryEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditEntryService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditEntryEntity entity);
}
