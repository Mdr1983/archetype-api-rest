package com.louzao.application.domain.service.util;

import com.louzao.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.louzao.application.domain.ports.secondary.repository.EntitySpecification;
import com.louzao.application.domain.service.exceptions.DuplicatedItemException;
import com.louzao.application.domain.service.exceptions.ItemNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EntityService {

  //@Autowired
  //private CategoryRepository categoryRepository;

  @SneakyThrows
  public <T> T getEntityById(Long id, Class<T> clazz, boolean throwExceptionWithItemNotFound,
      boolean throwExceptionWithDuplicatedItem) {
    if (id == null) {
      throw new IllegalArgumentException("The value of the id field cannot be null");
    }

    Object entity = null;
    /*if (clazz == CategoryEntity.class) {
      entity = categoryRepository.findById(id.intValue());
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getName());
    }*/

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
    /*if (clazz == CategoryEntity.class) {
      entity = categoryRepository.findByCode(code);
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getSimpleName());
    }*/

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
    /*if (clazz == CategoryEntity.class) {
      entity = new EntitySpecification<CategoryEntity>(dto);
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getName());
    }*/

    return ((EntitySpecification<T>) entity);
  }
}
