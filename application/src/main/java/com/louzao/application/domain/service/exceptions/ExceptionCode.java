package com.louzao.application.domain.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ExceptionCode {

  UNKNOWN_ERROR("-9999", "Unknown error"),
  SQL_BAD_EXECUTION("0001", "SQL Execution error"),
  BAD_PARAMS("0002", "Bad params"),
  REQUIRED_ITEM("0003", "Required item"),
  DUPLICATED_ITEM("0004", "Duplicated item"),
  ITEM_NOT_FOUND("0005", "Not found item"),
  ITEM_VALUE_ERROR("0006", "Item value not valid"),
  GENERIC_ADAPTER_EXCEPTION("0007", "Generic Adapter Exception"),
  CRITERIAN_EXCEPTION("0008", "Criterian paameters is not valid"),
  VALIDATE_JSON_EXCEPTION("0009", "Json schema is not valid"),
  BAD_FORMAT("010", "Bad format"),
  RECOVERY_PERSISTENCE("011", "Recovery persistence error"),
  ITEM_IN_USE("012", "Item value not valid"),
  EXCEL_EXPORT_ERROR("014", "Excel export error");

  private final String code;
  private final String name;

}
