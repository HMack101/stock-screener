package com.hmack101.screener.service;

import com.hmack101.screener.dto.ExternalStockData;
import com.hmack101.screener.dto.StockDTO;
import com.hmack101.screener.model.Stock;
import com.hmack101.screener.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ExternalStockService externalStockService;


    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public List<StockDTO> listStocks() {
        return stockRepository.findAll()
                .stream()
                .map(StockDTO::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Stock> getStockById(Integer id) {
        return stockRepository.findById(id);
    }

    public Optional<Stock> getStockByTicker(String ticker) {
        return stockRepository.findByTicker(ticker);
    }

    public Stock createOrUpdateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(Integer id) {
        stockRepository.deleteById(id);
    }

    public boolean existsByTicker(String ticker) {
        return stockRepository.existsByTicker(ticker);
    }

    public Stock addOrUpdateStock(String ticker) {
        System.out.println("StockService - addOrUpdateStock - ticker: " + ticker);
        Optional<Stock> existing = stockRepository.findByTicker(ticker);

        if (existing.isPresent()) {
            return existing.get(); // Or update some fields if needed
        }

        // Enrich with external data if stock is new
        ExternalStockData data = externalStockService.fetchStockData(ticker);

        Stock newStock = new Stock();
        newStock.setTicker(data.getTicker());
        newStock.setFloatShares(data.getFloatShares());
        newStock.setAvgVolume(data.getAvgVolume());
        System.out.println("saving sock data: " + newStock);
        return stockRepository.save(newStock);
    }

    public Stock addOrUpdateStock(StockDTO dto) {
        Optional<Stock> existingStock = stockRepository.findByTicker(dto.getTicker());

        Stock stock = existingStock.orElseGet(Stock::new);
        stock.setTicker(dto.getTicker());
        stock.setFloatShares(dto.getFloatShares());
        stock.setAvgVolume(dto.getAvgVolume());

        return stockRepository.save(stock);
    }


}

