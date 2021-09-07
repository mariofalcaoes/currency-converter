package com.jaya.currency.converter.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used to isolate transactionEntity only for the repository.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionRequest {

  private Long          id;
  private LocalDateTime dateOperation;
  private String       destineCoin;
  private BigDecimal    destineValue;
  private String        originCoin;
  private BigDecimal    originValue;
  private BigDecimal    tax;
  private Long          userId;
}
