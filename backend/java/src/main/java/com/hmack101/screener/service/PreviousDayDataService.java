package com.hmack101.screener.service;

import com.hmack101.screener.model.PreviousDayData;
import com.hmack101.screener.repository.PreviousDayDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreviousDayDataService {

    private final PreviousDayDataRepository repository;

    @Autowired
    public PreviousDayDataService(PreviousDayDataRepository repository) {
        this.repository = repository;
    }

    public List<PreviousDayData> getAllData() {
        return repository.findAll();
    }

    public Optional<PreviousDayData> getById(Integer id) {
        return repository.findById(id);
    }

    public Optional<PreviousDayData> getByTicker(String ticker) {
        return repository.findByTicker(ticker);
    }

    public PreviousDayData save(PreviousDayData data) {
        return repository.save(data);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
