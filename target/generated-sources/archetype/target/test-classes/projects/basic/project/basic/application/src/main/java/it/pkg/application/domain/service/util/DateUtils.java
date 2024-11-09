package it.pkg.application.domain.service.util;

import it.pkg.application.domain.service.exceptions.BadFormatException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

  private DateUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static Date checkAndParseDateFormat(String dateString, String dateFormatString) throws BadFormatException {
    DateFormat dateFormat = new java.text.SimpleDateFormat(dateFormatString);
    dateFormat.setLenient(false);
    try {
      return dateFormat.parse(dateString);
    } catch (ParseException e) {
      log.error("Error while checking date {}, bad format, expected: {}", dateString, dateFormatString);
      throw new BadFormatException(
          String.format("Error while checking date %s, bad format, expected: %s", dateString, dateFormatString));
    }
  }
}
