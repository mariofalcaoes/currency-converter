package com.jaya.currency.converter.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Main entity of the database.
 */
@Entity(name = "transactions")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long          id;
  private LocalDateTime dateOperation;
  private String        destineCoin;
  private BigDecimal    destineValue;
  private String        originCoin;
  private BigDecimal    originValue;
  private BigDecimal    tax;
  private Long          userId;
}
