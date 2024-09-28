package com.mdemanuel.archetype_logistic_api.application.domain.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
  UNKNOWN_ERROR("-9999", "Unknown error"),
  SQL_BAD_EXECUTION("0001", "SQL Execution error."),
  BAD_PARAMS("0002", "Bad params"),
  REQUIRED_ITEM("0003", "Required item"),
  DUPLICATED_ITEM("0004", "Duplicated item"),
  ITEM_NOT_FOUND("0005", "Not found item"),
  ITEM_VALUE_ERROR("0006", "Item value not valid"),
  GENERIC_ADAPTER_EXCEPTION("0007", "Generic Adapter Exception.");

  private final String code;
  private final String name;
}
