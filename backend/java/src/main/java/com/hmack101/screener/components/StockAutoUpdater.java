package com.hmack101.screener.components;

import com.hmack101.screener.dto.ExternalStockData;
import com.hmack101.screener.model.Stock;
import com.hmack101.screener.repository.StockRepository;
import com.hmack101.screener.service.ExternalStockService;
import com.hmack101.screener.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockAutoUpdater {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ExternalStockService externalStockService;

    @Autowired
    private StockService stockService;

    // Run every 5 minutes (300,000 ms)
    @Scheduled(fixedRate = 300000)
    public void updateAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            try {
                ExternalStockData data = externalStockService.fetchStockData(stock.getTicker());

                stock.setFloatShares(data.getFloatShares());
                stock.setAvgVolume(data.getAvgVolume());

                stockRepository.save(stock);
                System.out.println("Updated: " + stock.getTicker());
            } catch (Exception e) {
                System.err.println("Failed to update " + stock.getTicker() + ": " + e.getMessage());
            }
        }
    }
}

