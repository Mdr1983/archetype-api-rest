package com.mdemanuel.application.domain.service.util;

import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryDocument;
import com.mdemanuel.application.domain.model.domain.mongo.master.CategoryGenericDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderDocument;
import com.mdemanuel.application.domain.model.domain.mongo.purchase_order.PurchaseOrderGenericDocument;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.master.CategoryDocumentRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.master.impl.CustomCategoryGenericDocumentRepositoryImpl;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order.impl.CustomPurchaseOrderGenericDocumentRepositoryImpl;
import com.mdemanuel.application.domain.ports.secondary.repository.mongo.purchase_order.PurchaseOrderDocumentRepository;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
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
  private CustomCategoryGenericDocumentRepositoryImpl customCategoryGenericDocumentRepositoryImpl;
  @Autowired
  private PurchaseOrderDocumentRepository purchaseOrderDocumentRepository;
  @Autowired
  private CustomPurchaseOrderGenericDocumentRepositoryImpl customPurchaseOrderGenericDocumentRepositoryImpl;

  @SneakyThrows
  public <T> T getDocumentById(String id, Class<T> clazz, boolean throwExceptionWithItemNotFound,
      boolean throwExceptionWithDuplicatedItem) {
    if (id == null) {
      throw new IllegalArgumentException("The value of the id field cannot be null");
    }

    Object document = null;
    if (clazz == CategoryDocument.class) {
      document = categoryDocumentRepository.findById(id).orElse(null);
    } else if (clazz == CategoryGenericDocument.class) {
      document = customCategoryGenericDocumentRepositoryImpl.findById(id).orElse(null);
    } else if (clazz == PurchaseOrderDocument.class) {
      document = purchaseOrderDocumentRepository.findById(id);
    } else if (clazz == PurchaseOrderGenericDocument.class) {
      document = customPurchaseOrderGenericDocumentRepositoryImpl.findById(id);
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
      document = customCategoryGenericDocumentRepositoryImpl.findByCode(code);
    } else if (clazz == PurchaseOrderDocument.class) {
      document = purchaseOrderDocumentRepository.findByCode(code);
    } else if (clazz == PurchaseOrderGenericDocument.class) {
      document = customPurchaseOrderGenericDocumentRepositoryImpl.findByCode(code);
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
