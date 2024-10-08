package com.mdemanuel.application.domain.service.audit;

import com.mdemanuel.application.domain.model.domain.audit.AuditExitEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AuditExitService {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void save(AuditExitEntity entity);
}

