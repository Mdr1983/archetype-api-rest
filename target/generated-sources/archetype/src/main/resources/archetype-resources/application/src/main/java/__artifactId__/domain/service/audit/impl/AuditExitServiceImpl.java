#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.audit.impl;

import ${package}.${artifactId}.domain.model.domain.postgres.audit.AuditExitEntity;
import ${package}.${artifactId}.domain.ports.secondary.repository.postgres.audit.AuditExitRepository;
import ${package}.${artifactId}.domain.service.audit.AuditExitService;
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
