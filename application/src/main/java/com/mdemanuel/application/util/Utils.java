package com.mdemanuel.application.util;

import jakarta.validation.ConstraintViolation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

  private Utils() {
    throw new IllegalStateException("Utility class");
  }

  public static LocalDateTime joinDateTime(LocalDate date, LocalTime time) {
    return date.atStartOfDay().withHour(time.getHour()).withMinute(time.getMinute()).withSecond(time.getSecond())
        .withNano(time.getNano());
  }

  public static String formatLocalDateTime(LocalDate date, LocalTime time, String pattern) {
    return formatLocalDateTime(joinDateTime(date, time), pattern);
  }

  public static String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static LocalDate parseStringToLocalDate(String strDate, String pattern) {
    return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(pattern));
  }

  public static LocalDateTime parseStringToLocalDateTime(String strDate, String pattern) {
    return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(pattern));
  }

  public static LocalDateTime parseStringToLocalDateTime(String strDate) {
    return LocalDateTime.parse(strDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static <T> String getValidationErrors(Set<ConstraintViolation<T>> violations) {
    return violations.stream()
        .map(violation -> String.format("%s %s", violation.getPropertyPath(), violation.getMessage()))
        .collect(Collectors.joining(" ; "));
  }

}
