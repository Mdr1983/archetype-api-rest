package it.pkg.application.domain.ports.secondary.repository.mongo.master;

import it.pkg.application.domain.model.domain.mongo.master.CategoryDocument;
import it.pkg.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import it.pkg.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryDocumentRepository {

  CategoryDocument findByCode(String code);

  CategoryDocument findById(String id);

  List<CategoryDocument> findAll();

  Page<CategoryDocument> findAll(DocumentMongoSpecification documentMongoSpecification, Pageable pageable);

  <S extends CategoryDocument> S save(S entity);

  void delete(CategoryDocument entity);

  default void deleteById(String id) {
    CategoryDocumentRepository service = SpringBeanUtil.getInstance().getBean(CategoryDocumentRepository.class);

    CategoryDocument entity = findById(id);
    if (entity != null) {
      service.delete(entity);
    } else {
      throw new EntityNotFoundException(String.format("Not exists %s: %s", CategoryDocument.class.getSimpleName(), id));
    }
  }

  void deleteAll();
}
