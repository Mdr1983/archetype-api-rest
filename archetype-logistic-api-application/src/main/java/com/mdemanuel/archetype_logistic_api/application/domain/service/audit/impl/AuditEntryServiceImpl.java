package com.mdemanuel.archetype_logistic_api.application.domain.service.audit.impl;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.audit.AuditEntryEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository.audit.AuditEntryRepository;
import com.mdemanuel.archetype_logistic_api.application.domain.service.audit.AuditEntryService;
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
