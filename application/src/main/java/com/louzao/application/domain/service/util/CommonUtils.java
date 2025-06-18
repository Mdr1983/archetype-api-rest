package com.louzao.application.domain.service.util;

import com.louzao.application.domain.service.exceptions.BadParamsException;
import jakarta.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CommonUtils {

  public static String sanitize(@NotNull String value) {
    return StringUtils.stripAccents(value.trim());
  }

  public static Calendar getCalendarDate(SimpleDateFormat dateFormat, String value) throws BadParamsException {
    Date formattedDate = null;
    try {
      formattedDate = dateFormat.parse(value);
    } catch (ParseException e) {
      throw new BadParamsException(e);
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(formattedDate);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar;
  }
}
