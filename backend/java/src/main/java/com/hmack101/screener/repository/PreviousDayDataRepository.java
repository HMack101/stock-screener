package com.hmack101.screener.repository;

import com.hmack101.screener.model.PreviousDayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreviousDayDataRepository extends JpaRepository<PreviousDayData, Integer> {
    Optional<PreviousDayData> findByTicker(String ticker);
}