package com.mdemanuel.application.domain.ports.secondary.repository.postgres.audit;

import com.mdemanuel.application.domain.model.domain.postgres.audit.AuditEntryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuditEntryRepository extends CrudRepository<AuditEntryEntity, Integer> {

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  <S extends AuditEntryEntity> Iterable<S> saveAll(Iterable<S> entities);
}
