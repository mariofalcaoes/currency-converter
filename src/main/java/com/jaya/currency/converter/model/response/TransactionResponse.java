package com.jaya.currency.converter.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TransactionResponse used in the return of transactions historic.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionResponse {

  private Long          id;
  private LocalDateTime dateOperation;
  private String       destineCoin;
  private BigDecimal    destineValue;
  private String        originCoin;
  private BigDecimal    originValue;
  private BigDecimal    tax;
  @JsonIgnore
  private Long          userId;
}
