package com.jaya.currency.converter.model.mapper;

import com.jaya.currency.converter.model.entity.TransactionEntity;
import com.jaya.currency.converter.model.request.TransactionRequest;
import com.jaya.currency.converter.model.response.TransactionResponse;
import lombok.NonNull;

/**
 * Responsible for converting entities in order to isolate layers.
 */
public class TransactionMapper {

  private TransactionMapper() {

  }

  /**
   * TransactionEntity to TransactionResponse builder.
   */
  public static TransactionResponse map(@NonNull TransactionEntity transactionEntity) {
    return TransactionResponse.builder()
        .dateOperation(transactionEntity.getDateOperation())
        .destineCoin(transactionEntity.getDestineCoin())
        .originCoin(transactionEntity.getOriginCoin())
        .id(transactionEntity.getId())
        .tax(transactionEntity.getTax())
        .destineValue(transactionEntity.getDestineValue())
        .originValue(transactionEntity.getOriginValue())
        .userId(transactionEntity.getUserId())
        .build();
  }

  /**
   * TransactionRequest to TransactionEntity builder.
   */
  public static TransactionEntity map(@NonNull TransactionRequest transactionRequest) {
    return TransactionEntity.builder()
        .dateOperation(transactionRequest.getDateOperation())
        .destineCoin(transactionRequest.getDestineCoin())
        .originCoin(transactionRequest.getOriginCoin())
        .tax(transactionRequest.getTax())
        .destineValue(transactionRequest.getDestineValue())
        .originValue(transactionRequest.getOriginValue())
        .userId(transactionRequest.getUserId())
        .build();
  }

}
