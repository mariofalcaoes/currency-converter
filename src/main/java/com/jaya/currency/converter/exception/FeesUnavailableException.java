package com.jaya.currency.converter.exception;

import lombok.Getter;
import lombok.NonNull;

/**
 * Exception used if currency is not supported.
 */
@Getter
public class FeesUnavailableException extends RuntimeException {

  public FeesUnavailableException(@NonNull String message) {
    super(message);
  }
}
