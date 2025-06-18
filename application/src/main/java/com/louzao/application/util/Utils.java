package com.louzao.application.util;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class Utils {

  private Utils() {
    throw new IllegalStateException("Utility class");
  }

  public static void setMdcContext(Map<String, String> contextMap) {
    MDC.clear();

    if (contextMap != null) {
      MDC.setContextMap(contextMap);
    }
  }
}
