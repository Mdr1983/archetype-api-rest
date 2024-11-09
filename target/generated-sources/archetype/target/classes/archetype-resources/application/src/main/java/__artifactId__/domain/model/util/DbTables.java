#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.util;

public class DbTables {

  public static final String AUDIT_ENTRY = " [audit_entry] ";

  public static final String AUDIT_EXIT = " [audit_exit] ";

  public static final String CATEGORY = " [category] ";

  public static final String PURCHASE_ORDER = " [purchase_order] ";

  public static final String PURCHASE_ORDER_LINE = " [purchase_order_line] ";

  private DbTables() {
    throw new IllegalStateException("Utility class");
  }
}
