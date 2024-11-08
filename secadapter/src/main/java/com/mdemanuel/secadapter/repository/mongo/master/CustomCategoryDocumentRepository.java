package com.mdemanuel.secadapter.repository.mongo.master;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import com.mdemanuel.application.domain.ports.secondary.repository.MongoCriteriaPageableQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CustomCategoryDocumentRepository implements
    com.mdemanuel.application.domain.ports.secondary.repository.mongo.master.CustomCategoryDocumentRepository {

  @Autowired
  private MongoCriteriaPageableQuery mongoCriteriaPageableQuery;

  @Override
  public Page<CategoryDocument> findAll(DocumentMongoSpecification documentMongoSpecification, Pageable pageable) {
    return mongoCriteriaPageableQuery.find(documentMongoSpecification, pageable);
  }
}
