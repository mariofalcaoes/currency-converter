package com.jaya.currency.converter.service;

import com.jaya.currency.converter.config.ProjectProperties;
import com.jaya.currency.converter.exception.FeesUnavailableException;
import com.jaya.currency.converter.model.entity.TransactionEntity;
import com.jaya.currency.converter.model.request.ConversionRequest;
import com.jaya.currency.converter.model.request.TransactionRequest;
import com.jaya.currency.converter.model.response.FeesResponse;
import com.jaya.currency.converter.model.response.TransactionResponse;
import com.jaya.currency.converter.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  private ExchangeApiService    exchangeApiService;
  @Mock
  private ProjectProperties     projectProperties;
  @Mock
  private TransactionRepository transactionRepository;
  @Spy
  @InjectMocks
  private TransactionService    transactionService;

  @Test
  void convert() {

    // Configuring mocks
    var conversionRequest = ConversionRequest.builder()
        .destineCoin("BRL")
        .originCoin("USD")
        .originValue(BigDecimal.TEN)
        .userId(2L)
        .build();

    Map<String, BigDecimal> rates = new HashMap<>();
    rates.put("BRL", BigDecimal.ONE);
    rates.put("USD", BigDecimal.TEN);

    Mockito.doReturn(rates)
        .when(this.transactionService)
        .getRates();

    Mockito.doReturn(TransactionResponse.builder().build())
        .when(this.transactionService)
        .save(any(TransactionRequest.class));

    // Executing
    TransactionResponse transactionResponse = this.transactionService.convert(conversionRequest);

    // Validating
    assertNotNull(transactionResponse);

  }

  @Test
  void convertException() {

    // Configuring mocks
    var conversionRequest = ConversionRequest.builder()
        .destineCoin("XOF")
        .originCoin("USD")
        .originValue(BigDecimal.TEN)
        .userId(2L)
        .build();

    Map<String, BigDecimal> rates = new HashMap<>();
    rates.put("BRL", BigDecimal.ONE);
    rates.put("USD", BigDecimal.TEN);

    Mockito.doReturn(rates)
        .when(this.transactionService)
        .getRates();

    // Executing and Validating
    assertThrows(FeesUnavailableException.class,
        () -> this.transactionService.convert(conversionRequest));
  }

  @Test
  void findByUser() {
    // Configuring mocks

    Mockito.doReturn(Collections.singletonList(TransactionEntity.builder().build()))
        .when(this.transactionRepository)
        .findByUserId(anyLong());

    // Executing
    List<TransactionResponse> transactions = this.transactionService.findByUser(1L);

    // Validating
    assertNotNull(transactions);
    assertFalse(transactions.isEmpty());
  }

  @Test
  void getRates() {

    // Configuring mocks
    Mockito.doReturn("accessKey")
        .when(this.projectProperties)
        .getAccessKey();

    Map<String, BigDecimal> rates = new HashMap<>();
    rates.put("BRL", BigDecimal.ONE);
    rates.put("USD", BigDecimal.TEN);

    ResponseEntity<FeesResponse> responseEntity = new ResponseEntity<>(FeesResponse.builder()
        .rates(rates).build(), HttpStatus.OK);

    Mockito.doReturn(responseEntity)
        .when(this.exchangeApiService)
        .getFees(anyString());

    // Executing
    Map<String, BigDecimal> ratesResponse = this.transactionService.getRates();

    // Validating
    assertNotNull(ratesResponse);
    assertFalse(ratesResponse.isEmpty());
  }

  @Test
  void save() {

    // Configuring mocks
    TransactionRequest transactionRequest = TransactionRequest.builder().build();

    Mockito.doReturn(TransactionEntity.builder().build())
        .when(this.transactionRepository)
        .save(any(TransactionEntity.class));

    // Executing
    TransactionResponse transactionResponse = this.transactionService.save(transactionRequest);

    // Validating
    assertNotNull(transactionResponse);
  }
}