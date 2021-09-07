package com.jaya.currency.converter.service;

import com.jaya.currency.converter.config.ProjectProperties;
import com.jaya.currency.converter.model.mapper.TransactionMapper;
import com.jaya.currency.converter.model.request.ConversionRequest;
import com.jaya.currency.converter.model.request.TransactionRequest;
import com.jaya.currency.converter.model.response.FeesResponse;
import com.jaya.currency.converter.model.response.TransactionResponse;
import com.jaya.currency.converter.repository.TransactionRepository;
import com.jaya.currency.converter.util.BigDecimalUtil;
import com.jaya.currency.converter.util.DateUtil;
import com.jaya.currency.converter.util.TaxUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Responsible for converting currencies.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private static final int                   DEFAULT_PRECISION = 2;
  private final        ExchangeApiService    exchangeApiService;
  private final        ProjectProperties     projectProperties;
  private final        TransactionRepository transactionRepository;

  /**
   * This method calculates always based on the value of the euro currency.
   */
  public TransactionResponse convert(@NonNull ConversionRequest conversionRequest) {

    log.info("Converting values...");
    Map<String, BigDecimal> rates       = this.getRates();
    String                  originCoin  = conversionRequest.getOriginCoin().toUpperCase();
    String                  destineCoin = conversionRequest.getDestineCoin().toUpperCase();

    BigDecimal origin = BigDecimalUtil.setScale(TaxUtil.getCurrencyValue(originCoin, rates),
        DEFAULT_PRECISION);
    BigDecimal destine = BigDecimalUtil.setScale(TaxUtil.getCurrencyValue(destineCoin, rates),
        DEFAULT_PRECISION);

    BigDecimal originConvertedToEuro = BigDecimalUtil.setScale(conversionRequest.getOriginValue(),
        DEFAULT_PRECISION).divide(origin, RoundingMode.HALF_UP);
    BigDecimal destineConverted = originConvertedToEuro.multiply(destine);
    BigDecimal tax = destineConverted.divide(BigDecimalUtil.setScale(conversionRequest
        .getOriginValue(), DEFAULT_PRECISION), RoundingMode.HALF_UP);

    return this.save(TransactionRequest.builder()
        .dateOperation(DateUtil.now())
        .destineCoin(conversionRequest.getDestineCoin())
        .originCoin(conversionRequest.getOriginCoin())
        .tax(tax)
        .originValue(conversionRequest.getOriginValue())
        .destineValue(destineConverted)
        .userId(conversionRequest.getUserId())
        .build());
  }

  /**
   * Retrieve exchange API rates.
   */
  public Map<String, BigDecimal> getRates() {
    log.info("Recovering rates from exchange-api...");
    ResponseEntity<FeesResponse> responseEntity = this.exchangeApiService.getFees(
        this.projectProperties.getAccessKey());
    FeesResponse feesResponse = Optional.ofNullable(responseEntity.getBody())
        .orElseThrow();
    return feesResponse.getRates();
  }

  /**
   * Persist transaction in database.
   */
  public TransactionResponse save(@NonNull TransactionRequest transactionRequest) {
    log.info("Persisting transaction in database...");
    return TransactionMapper.map(this.transactionRepository.save(TransactionMapper
        .map(transactionRequest)));
  }


  /**
   * Retrieve user by id.
   */
  public List<TransactionResponse> findByUser(@NonNull Long userId) {
    return this.transactionRepository.findByUserId(userId)
        .parallelStream().map(TransactionMapper::map)
        .toList();
  }

}
