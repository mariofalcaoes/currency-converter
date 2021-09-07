package com.jaya.currency.converter.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal operations for code reuse.
 */
public class BigDecimalUtil {

  private BigDecimalUtil() {

  }

  public static BigDecimal setScale(BigDecimal value, int scale) {
    return value.setScale(scale, RoundingMode.HALF_UP);
  }


}
