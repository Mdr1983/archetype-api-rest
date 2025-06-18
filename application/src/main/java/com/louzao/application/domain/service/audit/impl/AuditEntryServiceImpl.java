package com.louzao.application.domain.service.audit.impl;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditEntryEntity;
import com.louzao.application.domain.ports.secondary.repository.mssql.audit.AuditEntryRepository;
import com.louzao.application.domain.service.audit.AuditEntryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditEntryServiceImpl implements AuditEntryService {

  @Autowired
  private AuditEntryRepository auditEntryRepository;

  @Override
  @SneakyThrows
  public void save(AuditEntryEntity auditEntryEntity) {
    auditEntryRepository.save(auditEntryEntity);
  }
}
