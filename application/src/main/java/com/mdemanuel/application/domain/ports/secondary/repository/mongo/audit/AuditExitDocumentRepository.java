package com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditExitDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuditExitDocumentRepository extends MongoRepository<AuditExitDocument, String> {

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  <S extends AuditExitDocument> S save(S entity);
}
