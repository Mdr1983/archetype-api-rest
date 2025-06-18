package com.louzao.application.domain.service.audit.impl;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditExitEntity;
import com.louzao.application.domain.ports.secondary.repository.mssql.audit.AuditExitRepository;
import com.louzao.application.domain.service.audit.AuditExitService;
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
