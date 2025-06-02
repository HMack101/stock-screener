package com.hmack101.screener.service;

import com.hmack101.screener.model.Catalyst;
import com.hmack101.screener.repository.CatalystRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CatalystService {

    @Autowired
    private CatalystRepository catalystRepository;

    public List<Catalyst> getAllCatalysts() {
        return catalystRepository.findAll();
    }

    public Optional<Catalyst> getCatalystById(Integer id) {
        return catalystRepository.findById(id);
    }

    public List<Catalyst> getCatalystsByTicker(String ticker) {
        return catalystRepository.findByTicker(ticker);
    }

    public List<Catalyst> getHighImpactCatalysts() {
        return catalystRepository.findByIsHighImpactTrue();
    }

    public List<Catalyst> getCatalystsByTickerAndType(String ticker, String type) {
        return catalystRepository.findByTickerAndType(ticker, type);
    }

    public Catalyst createOrUpdateCatalyst(Catalyst catalyst) {
        return catalystRepository.save(catalyst);
    }

    public void deleteCatalyst(Integer id) {
        catalystRepository.deleteById(id);
    }
}

