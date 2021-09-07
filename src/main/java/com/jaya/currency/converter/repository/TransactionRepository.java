package com.jaya.currency.converter.repository;

import com.jaya.currency.converter.model.entity.TransactionEntity;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to abstract interaction with the database.
 */
@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

  @Cacheable(value = "user-transactions", cacheManager = "cacheManagerGeneric")
  List<TransactionEntity> findByUserId(Long userId);
}
