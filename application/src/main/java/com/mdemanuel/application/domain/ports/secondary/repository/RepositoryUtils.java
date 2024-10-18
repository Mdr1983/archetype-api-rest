package com.mdemanuel.application.domain.ports.secondary.repository;

import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RepositoryUtils {

  public static Pageable getPageable(SearchCriteriaDto dto) {
    return PageRequest.of(dto.getPage(), dto.getSize() < 1 ? Integer.MAX_VALUE : dto.getSize(),
        Sort.by(dto.getSortDirection(), dto.getSortField()));
  }
}
