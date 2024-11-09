#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.audit.impl;

import ${package}.${artifactId}.domain.model.domain.postgres.audit.AuditEntryEntity;
import ${package}.${artifactId}.domain.ports.secondary.repository.postgres.audit.AuditEntryRepository;
import ${package}.${artifactId}.domain.service.audit.AuditEntryService;
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
