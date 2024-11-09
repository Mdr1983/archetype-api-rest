package it.pkg.application.domain.service.audit;

import it.pkg.application.domain.model.domain.postgres.audit.AuditExitEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditExitService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditExitEntity entity);
}

