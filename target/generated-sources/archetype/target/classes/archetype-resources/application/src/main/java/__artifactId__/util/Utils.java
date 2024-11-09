#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

  private Utils() {
    throw new IllegalStateException("Utility class");
  }

}
