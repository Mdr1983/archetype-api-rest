package com.mdemanuel.application.domain.ports.secondary.repository;

import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.pojo.OperatorsFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class EntitySpecification<T> implements Specification<T> {

  private SearchCriteriaDto criteria;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    return toPredicate(root, query, builder, criteria.getSearchCriteriaGroup());
  }

  private Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
      SearchCriteriaGroupDto criteria) {
    if (criteria.getCriteriaGroup() != null && !criteria.getCriteriaGroup().getSearchCriteriaGroup().isEmpty()) {
      List<Predicate> predicates = criteria.getCriteriaGroup().getSearchCriteriaGroup()
          .stream()
          .map(child -> toPredicate(root, query, builder, child))
          .collect(Collectors.toList());

      if (criteria.getOperator().equals(OperatorsFilter.AND)) {
        return builder.and(predicates.toArray(new Predicate[0]));
      } else if (criteria.getOperator().equals(OperatorsFilter.OR)) {
        return builder.or(predicates.toArray(new Predicate[0]));
      }

      throw new IllegalArgumentException();
    } else {
      return toPredicateSimple(root, query, builder, criteria);
    }
  }

  private Predicate toPredicateSimple(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
      SearchCriteriaGroupDto criteria) {
    switch (criteria.getOperator()) {
      case GREATER_THAN:
        return builder.greaterThan(root.<String>get(criteria.getAttribute()), criteria.getValueList().get(0));
      case LESS_THAN:
        return builder.lessThan(root.<String>get(criteria.getAttribute()), criteria.getValueList().get(0));
      case GREATER_THAN_OR_EQUAL_TO:
        return builder.greaterThanOrEqualTo(root.<String>get(criteria.getAttribute()), criteria.getValueList().get(0));
      case LESS_THAN_OR_EQUAL_TO:
        return builder.lessThanOrEqualTo(root.<String>get(criteria.getAttribute()), criteria.getValueList().get(0));
      case CONTAINS:
        return builder.like(root.<String>get(criteria.getAttribute()), "%" + criteria.getValueList().get(0) + "%");
      case EQUALS:
        return builder.equal(root.get(criteria.getAttribute()), criteria.getValueList().get(0));
      case NOT_EQUALS:
        return builder.notEqual(root.<String>get(criteria.getAttribute()), criteria.getValueList().get(0));
      case EMPTY:
        return builder.or(
            builder.isNull(root.get(criteria.getAttribute())),
            builder.equal(root.get(criteria.getAttribute()), ""));
      case NOT_EMPTY:
        return builder.and(
            builder.isNotNull(root.get(criteria.getAttribute())),
            builder.notEqual(root.get(criteria.getAttribute()), ""));
      case STARTS_WITH:
        return builder.like(root.<String>get(criteria.getAttribute()), "^" + criteria.getValueList().get(0));
      case ENDS_WITH:
        return builder.like(root.<String>get(criteria.getAttribute()), criteria.getValueList().get(0) + "$");
      case BOOLEAN:
        if (Boolean.getBoolean(criteria.getValueList().get(0))) {
          return builder.isTrue(root.<Boolean>get(criteria.getAttribute()));
        }

        return builder.isFalse(root.<Boolean>get(criteria.getAttribute()));
      case EQUALS_DATE:
        return builder.equal(root.get(criteria.getAttribute()), criteria.getValueList().get(0));
      case BETWEEN_DATE:
        return builder.between(root.get(criteria.getAttribute()), criteria.getValueList().get(0),
            criteria.getValueList().get(1));
      case IN:
        CriteriaBuilder.In<String> inClause = builder.in(root.get(criteria.getAttribute()));

        for (String value : criteria.getValueList()) {
          inClause.value(value);
        }

        return inClause;
      case NOT_IN:
        CriteriaBuilder.In<String> notInClause = builder.in(root.get(criteria.getAttribute()));

        for (String value : criteria.getValueList()) {
          notInClause.value(value);
        }

        return builder.not(notInClause);
      default:
        throw new IllegalArgumentException("Operator not supported: " + criteria.getOperator());
    }
  }
}
