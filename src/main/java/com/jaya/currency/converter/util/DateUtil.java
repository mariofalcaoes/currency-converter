package com.jaya.currency.converter.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Date operations for code reuse.
 */
public record DateUtil() {

  public static LocalDateTime now() {
    return LocalDateTime.now(ZoneId.of("UTC"));
  }

}
