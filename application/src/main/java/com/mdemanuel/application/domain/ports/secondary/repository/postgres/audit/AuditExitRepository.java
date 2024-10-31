package com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditExitEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuditExitRepository extends CrudRepository<AuditExitEntity, Integer> {

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  <S extends AuditExitEntity> S save(S entity);
}
