#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.service.util;

import ${package}.${artifactId}.domain.model.domain.mongo.master.CategoryDocument;
import ${package}.${artifactId}.domain.model.domain.mongo.master.CategoryGenericDocument;
import ${package}.${artifactId}.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import ${package}.${artifactId}.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import ${package}.${artifactId}.domain.ports.primary.dto.request.SearchCriteriaDto;
import ${package}.${artifactId}.domain.ports.secondary.repository.DocumentMongoSpecification;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.GenericDocumentRepository;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.master.CategoryDocumentRepository;
import ${package}.${artifactId}.domain.ports.secondary.repository.mongo.purchase_order.PurchaseOrderDocumentRepository;
import ${package}.${artifactId}.domain.service.exceptions.DuplicatedItemException;
import ${package}.${artifactId}.domain.service.exceptions.ItemNotFoundException;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentMongoService {

  @Autowired
  private CategoryDocumentRepository categoryDocumentRepository;
  @Autowired
  private GenericDocumentRepository<CategoryGenericDocument> categoryGenericDocumentRepository;
  @Autowired
  private PurchaseOrderDocumentRepository purchaseOrderDocumentRepository;
  @Autowired
  private GenericDocumentRepository<PurchaseOrderGenericDocument> purchaseOrderGenericDocumentRepository;

  @SneakyThrows
  public <T> T getDocumentById(String id, Class<T> clazz, boolean throwExceptionWithItemNotFound,
      boolean throwExceptionWithDuplicatedItem) {
    if (id == null) {
      throw new IllegalArgumentException("The value of the id field cannot be null");
    }

    Object document = null;
    if (clazz == CategoryDocument.class) {
      document = categoryDocumentRepository.findById(id);
    } else if (clazz == CategoryGenericDocument.class) {
      document = categoryGenericDocumentRepository.findById(id);
    } else if (clazz == PurchaseOrderDocument.class) {
      document = purchaseOrderDocumentRepository.findById(id);
    } else if (clazz == PurchaseOrderGenericDocument.class) {
      document = purchaseOrderGenericDocumentRepository.findById(id);
    } else {
      throw new IllegalArgumentException("Invalid document class: " + clazz.getName());
    }

    if (document == null && throwExceptionWithItemNotFound) {
      throw new ItemNotFoundException(clazz.getSimpleName(), "Id", id.toString());
    }

    if (document != null && throwExceptionWithDuplicatedItem) {
      throw new DuplicatedItemException(clazz.getSimpleName(), "Id", id.toString());
    }

    return clazz.cast(document);
  }

  @SneakyThrows
  public <T> T getDocumentByCode(String code, Class<T> clazz, boolean throwExceptionWithItemNotFound,
      boolean throwExceptionWithDuplicatedItem) {
    if (code == null) {
      throw new IllegalArgumentException("The value of the code field cannot be null");
    }

    Object document = null;
    if (clazz == CategoryDocument.class) {
      document = categoryDocumentRepository.findByCode(code);
    } else if (clazz == CategoryGenericDocument.class) {
      document = categoryGenericDocumentRepository.findByCode(code);
    } else if (clazz == PurchaseOrderDocument.class) {
      document = purchaseOrderDocumentRepository.findByCode(code);
    } else if (clazz == PurchaseOrderGenericDocument.class) {
      document = purchaseOrderGenericDocumentRepository.findByCode(code);
    } else {
      throw new IllegalArgumentException("Invalid document class: " + clazz.getSimpleName());
    }

    if (document == null && throwExceptionWithItemNotFound) {
      throw new ItemNotFoundException(clazz.getSimpleName(), "Code", code);
    }

    if (document != null && throwExceptionWithDuplicatedItem) {
      throw new DuplicatedItemException(clazz.getSimpleName(), "Code", code);
    }

    return clazz.cast(document);
  }

  @SneakyThrows
  public <T> DocumentMongoSpecification<T> getDocumentMongoSpecification(Class<T> clazz, SearchCriteriaDto dto) {
    Set<Class<?>> set = Set.of(CategoryDocument.class, CategoryGenericDocument.class, PurchaseOrderDocument.class,
        PurchaseOrderGenericDocument.class);

    if (!set.contains(clazz)) {
      throw new IllegalArgumentException("Invalid document class: " + clazz.getName());
    }

    return new DocumentMongoSpecification<T>(clazz, dto);
  }
}
