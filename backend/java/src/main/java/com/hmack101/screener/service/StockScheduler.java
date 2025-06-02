package com.hmack101.screener.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockScheduler {

    private final StockService stockService;

    public StockScheduler(StockService stockService) {
        this.stockService = stockService;
    }

    // Daily at 6:00 AM
    @Scheduled(cron = "0 0 6 * * *")
    public void refreshFloatAndVolume() {
        System.out.println("Running daily float/volume refresh...");
        for (var stock : stockService.listStocks()) {
            try {
                stockService.addOrUpdateStock(stock.getTicker());
            } catch (Exception e) {
                System.err.println("Failed to update: " + stock.getTicker() + " - " + e.getMessage());
            }
        }
    }

    // Every 15 minutes (placeholder: implement news + filings fetch logic)
    @Scheduled(cron = "0 */15 * * * *")
    public void fetchAndStoreCatalysts() {
        System.out.println("Running catalyst update (news + filings) every 15 minutes...");
        // You would implement the logic using SEC & Polygon APIs here.
        // Ideally through a CatalystService or extend StockService
    }
}
