package com.jaya.currency.converter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generic payload to return some error for the user.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorStatusResponse {
  private String description;
}

