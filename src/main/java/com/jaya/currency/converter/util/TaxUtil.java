package com.jaya.currency.converter.util;

import com.jaya.currency.converter.exception.FeesUnavailableException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * TaxUtil operations for code reuse.
 */
public record TaxUtil() {

  /**
   * Check if currency is allowed, and than, return the value.
   */
  public static BigDecimal getCurrencyValue(String tax, Map<String, BigDecimal> rates) {

    return Optional.ofNullable(rates.get(tax)).orElseThrow(() ->
        new FeesUnavailableException("Currency not supported"));

  }

}
