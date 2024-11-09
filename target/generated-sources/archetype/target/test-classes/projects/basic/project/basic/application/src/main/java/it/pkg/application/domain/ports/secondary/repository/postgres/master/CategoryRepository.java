package it.pkg.application.domain.ports.secondary.repository.postgres.master;

import it.pkg.application.domain.model.domain.postgres.master.CategoryEntity;
import it.pkg.application.util.SpringBeanUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CategoryRepository {

  CategoryEntity findByCode(String code);

  CategoryEntity findById(Integer id);

  Iterable<CategoryEntity> findAll();

  Page<CategoryEntity> findAll(Specification<CategoryEntity> spec, Pageable pageable);

  CategoryEntity save(CategoryEntity entity);

  void delete(CategoryEntity entity);

  default void deleteById(Integer id) {
    CategoryRepository service = SpringBeanUtil.getInstance().getBean(CategoryRepository.class);

    CategoryEntity entity = findById(id);
    if (entity != null) {
      service.delete(entity);
    } else {
      throw new EntityNotFoundException(String.format("Not exists %s: %s", CategoryEntity.class.getSimpleName(), id));
    }
  }

  void deleteAll();
}
