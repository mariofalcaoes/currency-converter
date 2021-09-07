package com.jaya.currency.converter.resource;

import com.jaya.currency.converter.model.request.ConversionRequest;
import com.jaya.currency.converter.model.response.ConversionResponse;
import com.jaya.currency.converter.model.response.TransactionResponse;
import com.jaya.currency.converter.service.TransactionService;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class ConverterResourceTest {

  @InjectMocks
  private ConverterResource  converterResource;
  @Mock
  private TransactionService transactionService;

  @Test
  void convert() {

    // Configuring mocks
    var conversionRequest = ConversionRequest.builder()
        .destineCoin("BRL")
        .originCoin("USD")
        .originValue(BigDecimal.TEN)
        .build();

    Mockito.doReturn(TransactionResponse.builder().build())
        .when(this.transactionService)
        .convert(any(ConversionRequest.class));

    // Executing
    ResponseEntity<TransactionResponse> convert = this.converterResource.convert(conversionRequest,
        1L);

    // Validating
    assertTrue(convert.getStatusCode().is2xxSuccessful());

  }


  @Test
  void historic() {

    // Configuring mocks
    Mockito.doReturn(Collections.singletonList(TransactionResponse.builder().build()))
        .when(this.transactionService)
        .findByUser(anyLong());

    // Executing
    ResponseEntity<ConversionResponse> convert = this.converterResource.historic(1L);

    // Validating
    assertEquals(HttpStatus.OK, convert.getStatusCode());
  }

  @Test
  void historicEmpty() {

    // Configuring mocks
    Mockito.doReturn(Collections.emptyList())
        .when(this.transactionService)
        .findByUser(anyLong());

    // Executing
    ResponseEntity<ConversionResponse> convert = this.converterResource.historic(1L);

    // Validating
    assertEquals(HttpStatus.NO_CONTENT, convert.getStatusCode());
  }
}