package com.hmack101.screener.repository;

import com.hmack101.screener.model.Catalyst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalystRepository extends JpaRepository<Catalyst, Integer> {

    List<Catalyst> findByTicker(String ticker);

    List<Catalyst> findByIsHighImpactTrue();

    List<Catalyst> findByTickerAndType(String ticker, String type);
}
