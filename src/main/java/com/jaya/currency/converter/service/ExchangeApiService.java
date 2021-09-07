package com.jaya.currency.converter.service;

import com.jaya.currency.converter.model.response.FeesResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Client to retrieve currencies from exchange-api.
 */
@FeignClient(name = "exchange-api-client", url = "${api.exchange.url}")
public interface ExchangeApiService {

  @GetMapping
  @Cacheable(value = "rates-values", cacheManager = "cacheManagerGeneric")
  ResponseEntity<FeesResponse> getFees(@RequestParam(value = "access_key") String accessKey);

}
