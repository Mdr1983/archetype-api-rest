package com.mdemanuel.secadapter.repository.mongo.audit;

import com.mdemanuel.application.domain.model.domain.mongo.audit.AuditEntryDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.audit.AuditEntryDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AuditEntryDocumentRepositoryImpl implements AuditEntryDocumentRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public AuditEntryDocument save(AuditEntryDocument entity) {
    mongoTemplate.save(entity);

    return entity;
  }
}
