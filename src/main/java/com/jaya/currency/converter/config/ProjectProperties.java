package com.jaya.currency.converter.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Custom properties per environment.
 */
@Getter
@Configuration
@SuppressWarnings("unused")
public class ProjectProperties {
  @Value("${api.exchange.access_key}")
  private String accessKey;
  @Value("${cache.ttl}")
  private Long   cacheTtl;
}
