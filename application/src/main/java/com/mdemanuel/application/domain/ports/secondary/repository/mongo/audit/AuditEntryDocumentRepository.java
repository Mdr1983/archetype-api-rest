package com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditEntryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuditEntryDocumentRepository extends MongoRepository<AuditEntryDocument, String> {

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  <S extends AuditEntryDocument> S save(S entity);
}
