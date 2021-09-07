package com.jaya.currency.converter.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * CacheConfig.
 */
@Configuration
@EnableCaching
@SuppressWarnings(value = {"unused", "UnstableApiUsage", "NullableProblems"})
@RequiredArgsConstructor
public class CacheConfig extends CachingConfigurerSupport {

  private final ProjectProperties projectProperties;

  @Override
  @Bean(name = "cacheManagerGeneric")
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager() {

      @Override
      protected Cache createConcurrentMapCache(final String name) {
        return new ConcurrentMapCache(name, CacheBuilder.newBuilder()
            .expireAfterWrite(projectProperties.getCacheTtl(), TimeUnit.SECONDS)
            .build(new CacheLoader<>() {
              @Override
              public Object load(@Nullable Object object) {
                return null;
              }
            }).asMap(), false);
      }
    };
  }

}