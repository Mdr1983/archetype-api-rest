package it.pkg.application.domain.service.audit.impl;

import it.pkg.application.domain.model.domain.postgres.audit.AuditEntryEntity;
import it.pkg.application.domain.ports.secondary.repository.postgres.audit.AuditEntryRepository;
import it.pkg.application.domain.service.audit.AuditEntryService;
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
