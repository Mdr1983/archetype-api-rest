package com.mdemanuel.application.domain.service.audit.impl;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditEntryEntity;
import com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit.AuditEntryRepository;
import com.mdemanuel.application.domain.service.audit.AuditEntryService;
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
