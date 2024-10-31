package com.mdemanuel.application.domain.ports.secondary.repository;

import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto.SearchCriteriaGroupDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.pojo.OperatorsFilter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@AllArgsConstructor
@Data
public class DocumentMongoSpecification<T> {

  private Class<T> clazz;
  private SearchCriteriaDto criteria;

  public Query toQuery() {
    Query query = new Query();
    query.addCriteria(toCriteria(criteria.getSearchCriteriaGroup()));
    query.with(RepositoryUtils.getPageable(criteria));

    return query;
  }

  private Criteria toCriteria(SearchCriteriaGroupDto criteriaGroup) {
    if (criteriaGroup.getCriteriaGroup() != null && !criteriaGroup.getCriteriaGroup().getSearchCriteriaGroup()
        .isEmpty()) {
      List<Criteria> criteriaList = criteriaGroup.getCriteriaGroup().getSearchCriteriaGroup()
          .stream()
          .map(this::toCriteria)
          .collect(Collectors.toList());

      if (criteriaGroup.getOperator().equals(OperatorsFilter.AND)) {
        return new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
      } else if (criteriaGroup.getOperator().equals(OperatorsFilter.OR)) {
        return new Criteria().orOperator(criteriaList.toArray(new Criteria[0]));
      }
      throw new IllegalArgumentException("Operator not supported: " + criteriaGroup.getOperator());
    } else {
      return toSimpleCriteria(criteriaGroup);
    }
  }

  private Criteria toSimpleCriteria(SearchCriteriaGroupDto criteria) {
    String attribute = criteria.getAttribute();
    List<String> values = criteria.getValueList();

    switch (criteria.getOperator()) {
      case GREATER_THAN:
        return Criteria.where(attribute).gt(values.get(0));
      case LESS_THAN:
        return Criteria.where(attribute).lt(values.get(0));
      case GREATER_THAN_OR_EQUAL_TO:
        return Criteria.where(attribute).gte(values.get(0));
      case LESS_THAN_OR_EQUAL_TO:
        return Criteria.where(attribute).lte(values.get(0));
      case CONTAINS:
        return Criteria.where(attribute).regex(".*" + values.get(0) + ".*", "i");
      case EQUALS:
        return Criteria.where(attribute).is(values.get(0));
      case NOT_EQUALS:
        return Criteria.where(attribute).ne(values.get(0));
      case EMPTY:
        return new Criteria().orOperator(
            Criteria.where(attribute).is(null),
            Criteria.where(attribute).is(""));
      case NOT_EMPTY:
        return new Criteria().andOperator(
            Criteria.where(attribute).ne(null),
            Criteria.where(attribute).ne(""));
      case STARTS_WITH:
        return Criteria.where(attribute).regex("^" + values.get(0), "i");
      case ENDS_WITH:
        return Criteria.where(attribute).regex(values.get(0) + "$", "i");
      case BOOLEAN:
        boolean booleanValue = Boolean.parseBoolean(values.get(0));
        return Criteria.where(attribute).is(booleanValue);
      case EQUALS_DATE:
        return Criteria.where(attribute).is(values.get(0));
      case BETWEEN_DATE:
        return Criteria.where(attribute).gte(values.get(0)).lte(values.get(1));
      case IN:
        return Criteria.where(attribute).in(values);
      case NOT_IN:
        return Criteria.where(attribute).nin(values);
      default:
        throw new IllegalArgumentException("Operator not supported: " + criteria.getOperator());
    }
  }
}