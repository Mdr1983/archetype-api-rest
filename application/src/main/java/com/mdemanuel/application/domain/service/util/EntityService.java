package com.mdemanuel.application.domain.service.util;

import com.mdemanuel.application.domain.model.domain.master.CategoryEntity;
import com.mdemanuel.application.domain.model.domain.order.PurchaseOrderEntity;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.secondary.repository.EntitySpecification;
import com.mdemanuel.application.domain.ports.secondary.repository.master.CategoryRepository;
import com.mdemanuel.application.domain.ports.secondary.repository.purchase_order.PurchaseOrderRepository;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EntityService {

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private PurchaseOrderRepository purchaseOrderRepository;

  @SneakyThrows
  public <T> T getEntityById(Long id, Class<T> clazz, boolean throwExceptionWithItemNotFound,
      boolean throwExceptionWithDuplicatedItem) {
    if (id == null) {
      throw new IllegalArgumentException("The value of the id field cannot be null");
    }

    Object entity = null;
    if (clazz == CategoryEntity.class) {
      entity = categoryRepository.findById(id.intValue()).orElse(null);
    } else if (clazz == PurchaseOrderEntity.class) {
      entity = purchaseOrderRepository.findById(id.intValue());
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getName());
    }

    if (entity == null && throwExceptionWithItemNotFound) {
      throw new ItemNotFoundException(clazz.getSimpleName(), "Id", id.toString());
    }

    if (entity != null && throwExceptionWithDuplicatedItem) {
      throw new DuplicatedItemException(clazz.getSimpleName(), "Id", id.toString());
    }

    return clazz.cast(entity);
  }

  @SneakyThrows
  public <T> T getEntityByCode(String code, Class<T> clazz, boolean throwExceptionWithItemNotFound,
      boolean throwExceptionWithDuplicatedItem) {
    if (code == null) {
      throw new IllegalArgumentException("The value of the code field cannot be null");
    }

    Object entity = null;
    if (clazz == CategoryEntity.class) {
      entity = categoryRepository.findByCategoryCode(code);
    } else if (clazz == PurchaseOrderEntity.class) {
      entity = purchaseOrderRepository.findByPurchaseOrderCode(code);
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getSimpleName());
    }

    if (entity == null && throwExceptionWithItemNotFound) {
      throw new ItemNotFoundException(clazz.getSimpleName(), "Code", code);
    }

    if (entity != null && throwExceptionWithDuplicatedItem) {
      throw new DuplicatedItemException(clazz.getSimpleName(), "Code", code);
    }

    return clazz.cast(entity);
  }

  @SneakyThrows
  public <T> EntitySpecification<T> getEntitySpecification(Class<T> clazz, SearchCriteriaDto dto) {
    Object entity = null;
    if (clazz == CategoryEntity.class) {
      entity = new EntitySpecification<CategoryEntity>(dto);
    } else if (clazz == PurchaseOrderEntity.class) {
      entity = new EntitySpecification<PurchaseOrderEntity>(dto);
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getName());
    }

    return ((EntitySpecification<T>) entity);
  }
}
