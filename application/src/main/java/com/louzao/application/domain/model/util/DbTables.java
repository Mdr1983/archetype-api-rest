package com.louzao.application.domain.model.util;

public class DbTables {

  public static final String AUDIT_ENTRY = " [audit_entry] ";

  public static final String AUDIT_EXIT = " [audit_exit] ";

  public static final String VW_INPUT_VAT = " [vw_input_vat] ";

  public static final String VW_OUTPUT_VAT = " [vw_output_vat] ";

  public static final String VW_INPUT_VAT_CONCILIATION = " [vw_intput_vat_conciliation] ";

  public static final String VW_OUTPUT_VAT_CONCILIATION = " [vw_output_vat_conciliation] ";

  public static final String VW_VAT_ACCOUNT_BALANCE = " [vw_vat_account_balance] ";

  private DbTables() {
    throw new IllegalStateException("Utility class");
  }
}
