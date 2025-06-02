package com.hmack101.screener.service;

import com.hmack101.screener.dto.ExternalStockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ExternalStockService {

    @Autowired
    private PolygonClient polygonClient;

    @Autowired
    private YahooFallbackService yahooFallbackService;

    private static final int MAX_RETRIES = 3;

    public ExternalStockData fetchStockData(String ticker) {
        ticker = ticker.toUpperCase();
        ExternalStockData data = null;

        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            attempt++;
            try {
                data = polygonClient.fetchStockDetails(ticker);
                if (data != null && data.getAvgVolume() != null && data.getFloatShares() != null) {
                    // Got good data from Polygon, return it
                    return data;
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Attempt " + attempt + " failed for Polygon: " + e.getMessage());
                // Optionally add Thread.sleep() here to delay retries
            }
        }

        // Polygon failed or incomplete data — fallback to Yahoo
        try {
            data = yahooFallbackService.fetchFromYahoo(ticker);
        } catch (IOException e) {
            System.err.println("Yahoo fallback also failed: " + e.getMessage());
            // Return minimal default to avoid null pointer downstream
            data = new ExternalStockData();
            data.setTicker(ticker);
            data.setAvgVolume(0d);
            data.setFloatShares(0d);
        }

        return data;
    }
}

