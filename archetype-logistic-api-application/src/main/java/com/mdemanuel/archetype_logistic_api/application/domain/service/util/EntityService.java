package com.mdemanuel.archetype_logistic_api.application.domain.service.util;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.master.DataTypeEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.secondary.repository.master.DataTypeRepository;
import com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EntityService {

  @Autowired
  private DataTypeRepository dataTypeRepository;

  public <T> T getEntityById(Long id, Class<T> clazz, boolean throwExceptionWithItemNotFound)
      throws ItemNotFoundException, IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("The value of the id field cannot be null");
    }

    Object entity = null;
    if (clazz == DataTypeEntity.class) {
      entity = dataTypeRepository.findById(id.intValue()).orElse(null);
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getName());
    }

    if (entity == null && throwExceptionWithItemNotFound) {
      throw new ItemNotFoundException(clazz.getSimpleName(), "Id", id.toString());
    }

    return clazz.cast(entity);
  }

  public <T> T getEntityByCode(String code, Class<T> clazz, boolean throwExceptionWithItemNotFound)
      throws ItemNotFoundException, IllegalArgumentException {
    if (code == null) {
      throw new IllegalArgumentException("The value of the code field cannot be null");
    }

    Object entity = null;
    if (clazz == DataTypeEntity.class) {
      entity = dataTypeRepository.findByCode(code);
    } else {
      throw new IllegalArgumentException("Invalid entity class: " + clazz.getSimpleName());
    }

    if (entity == null && throwExceptionWithItemNotFound) {
      throw new ItemNotFoundException(clazz.getSimpleName(), "Code", code);
    }

    return clazz.cast(entity);
  }
}
