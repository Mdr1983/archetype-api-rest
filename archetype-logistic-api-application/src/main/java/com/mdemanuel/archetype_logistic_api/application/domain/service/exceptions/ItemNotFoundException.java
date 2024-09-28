package com.mdemanuel.archetype_logistic_api.application.domain.service.exceptions;

import com.mdemanuel.archetype_logistic_api.application.domain.model.exception.BaseException;
import java.util.Arrays;
import java.util.List;

public class ItemNotFoundException extends BaseException {

  private static final long serialVersionUID = -8326169032596258004L;

  private static final ExceptionCode exceptionCode = ExceptionCode.ITEM_NOT_FOUND;

  public ItemNotFoundException(String message) {
    super(exceptionCode.getCode(), exceptionCode.getName(), message);
  }

  public ItemNotFoundException(String item, String value) {
    super(exceptionCode.getCode(), exceptionCode.getName(),
        String.format(
            "Not exists %s: %s", item, value
        ));
  }

  public ItemNotFoundException(String attribute, String... items) {
    super(exceptionCode.getCode(), exceptionCode.getName(), makeMessage(attribute, items));
  }

  private static String makeMessage(String attribute, String... items) {
    StringBuilder detail = new StringBuilder();
    List<String> values = Arrays.asList(items);
    if (items.length == 2) {
      detail.append(String.format("Not exists %s for %s: %s", attribute, values.get(0), values.get(1)));
    } else {
      detail.append(String.format("Not exists %s for ", attribute));
      for (int i = 0; i < values.size(); i++) {
        if (i % 2 == 0) {
          detail.append(values.get(i) + ": ");
        } else {
          if (i < values.size() - 3) {
            detail.append(values.get(i) + ", ");
          } else {
            detail.append((i == values.size() - 3) ? values.get(i) + " and " : values.get(i));
          }
        }
      }
    }
    return detail.toString();
  }

}
