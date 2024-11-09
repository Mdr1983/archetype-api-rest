#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.model.util;

public class DbDocuments {

  public static final String AUDIT_ENTRY = "audit_entry";

  public static final String AUDIT_EXIT = "audit_exit";

  public static final String CATEGORY = "category";

  public static final String CATEGORY_GENERIC = "category_generic";

  public static final String PURCHASE_ORDER = "purchase_order";

  public static final String PURCHASE_ORDER_GENERIC = "purchase_order_generic";

  private DbDocuments() {
    throw new IllegalStateException("Utility class");
  }
}
