package com.jaya.currency.converter.resource;

import com.jaya.currency.converter.model.request.ConversionRequest;
import com.jaya.currency.converter.model.response.ConversionResponse;
import com.jaya.currency.converter.model.response.TransactionResponse;
import com.jaya.currency.converter.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource to expose application endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ConverterResource {

  private final TransactionService transactionService;

  /**
   * Convert currencies.
   */
  @ApiOperation(value = "Conversion of money.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Conversion done with success."),
      @ApiResponse(code = 400, message = "Invalid parameters."),
      @ApiResponse(code = 500, message = "Internal error, contact the administrator.")
  })
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> convert(@Valid @RequestBody ConversionRequest
                                                        conversionRequest,
                                                     @RequestHeader("user_id") Long userId) {

    conversionRequest.setUserId(userId);

    log.info("Receiving money conversion  request {} ", conversionRequest);

    return ResponseEntity.ok(this.transactionService.convert(conversionRequest));
  }

  /**
   * Retrieve transaction historic.
   */
  @ApiOperation(value = "Conversion of money.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Conversion done with success."),
      @ApiResponse(code = 400, message = "Invalid parameters."),
      @ApiResponse(code = 500, message = "Internal error, contact the administrator.")
  })
  @GetMapping(value = "/historic/{userId}")
  public ResponseEntity<ConversionResponse> historic(@PathVariable("userId") Long userId) {

    log.info("Recovering transaction history for user {} ", userId);

    ConversionResponse conversionResponse = ConversionResponse.builder()
        .userId(userId)
        .transactionResponses(this.transactionService.findByUser(userId))
        .build();

    return new ResponseEntity<>(conversionResponse,
        conversionResponse.getTransactionResponses().isEmpty() ? HttpStatus.NO_CONTENT :
            HttpStatus.OK);
  }

}
