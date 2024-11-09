#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.secondary.repository.mongo.master;

import ${package}.${artifactId}.domain.model.domain.mongo.master.CategoryDocument;
import ${package}.${artifactId}.domain.ports.secondary.repository.DocumentMongoSpecification;
import ${package}.${artifactId}.util.SpringBeanUtil;
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
