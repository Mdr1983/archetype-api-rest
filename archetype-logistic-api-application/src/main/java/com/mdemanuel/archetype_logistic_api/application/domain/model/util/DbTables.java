package com.mdemanuel.archetype_logistic_api.application.domain.model.util;

public class DbTables {

  public static final String AUDIT_ENTRY = " [audit_entry] ";

  public static final String AUDIT_EXIT = " [audit_exit] ";

  public static final String DATA_TYPE = " [data_type] ";

  private DbTables() {
    throw new IllegalStateException("Utility class");
  }
}
