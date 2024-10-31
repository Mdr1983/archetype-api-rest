package com.mdemanuel.application.domain.ports.secondary.repository.mongo.master;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCategoryDocumentRepository {

  Page<CategoryDocument> findAll(DocumentMongoSpecification documentMongoSpecification, Pageable pageable);
}
