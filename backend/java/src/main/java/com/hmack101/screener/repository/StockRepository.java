package com.hmack101.screener.repository;

import com.hmack101.screener.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findByTicker(String ticker);

    boolean existsByTicker(String ticker);
}

