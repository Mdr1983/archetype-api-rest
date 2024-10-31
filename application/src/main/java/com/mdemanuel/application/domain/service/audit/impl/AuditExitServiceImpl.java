package com.mdemanuel.application.domain.service.audit.impl;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditExitEntity;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit.AuditExitRepository;
import com.mdemanuel.application.domain.service.audit.AuditExitService;
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
