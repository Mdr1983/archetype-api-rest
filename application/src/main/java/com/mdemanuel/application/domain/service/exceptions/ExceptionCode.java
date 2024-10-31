package com.mdemanuel.application.domain.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ExceptionCode {

  UNKNOWN_ERROR("-999", "Unknown error"),
  DUPLICATED_ITEM("001", "Duplicated item"),
  ITEM_NOT_FOUND("002", "Not found item"),
  ITEM_VALUE_ERROR("003", "Item value not valid"),
  RECOVERY_PERSISTENCE("004", "Recovery persistence error"),
  VAS_EMPTY("005", "Vas empty exception"),
  BAD_FORMAT("006", "Bad format"),
  DUPLICATED_PRIORITY("007", "Duplicated priority"),
  VAS_GROUP_DEPENDENCY("008", "Vas Group dependency"),
  VAS_SELECTOR_RULE_NOT_VALID("009", "Vas Selector Rule not valid"),
  INVALID_CODE("010", "Invalid Code"),
  VAS_ERROR("011", "Vas Error"),
  DUPLICATED_VAS_MATCHED_VAS_RULES("012", "Duplicated Vas in matched Vas rules"),
  ITEM_IN_USE("013", "Item in use");

  private final String code;
  private final String name;

}
