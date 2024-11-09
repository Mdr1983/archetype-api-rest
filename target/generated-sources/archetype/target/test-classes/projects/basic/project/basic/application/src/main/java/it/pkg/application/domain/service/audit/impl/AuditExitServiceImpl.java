package it.pkg.application.domain.service.audit.impl;

import it.pkg.application.domain.model.domain.postgres.audit.AuditExitEntity;
import it.pkg.application.domain.ports.secondary.repository.postgres.audit.AuditExitRepository;
import it.pkg.application.domain.service.audit.AuditExitService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditExitServiceImpl implements AuditExitService {

  @Autowired
  private AuditExitRepository auditExitRepository;

  @Override
  @SneakyThrows
  public void save(AuditExitEntity auditExitEntity) {
    auditExitRepository.save(auditExitEntity);
  }
}
