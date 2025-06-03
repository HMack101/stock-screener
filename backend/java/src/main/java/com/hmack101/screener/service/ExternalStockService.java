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
    private YahooFinanceClient yahooFinanceClient;

    private static final int MAX_RETRIES = 3;

    public ExternalStockData fetchStockData(String ticker) {
        ticker = ticker.toUpperCase();
        ExternalStockData data = new ExternalStockData();
        PolygonClient.PolygonData polygonData = null;
        YahooFinanceClient.YahooFinanceData yahooData = null;
        try {
            polygonData = polygonClient.fetchStockDetails(ticker);
            System.out.println("polygon data - float shares: " + polygonData.getFloatShares());
            System.out.println("polygon data - avg volume: " + polygonData.getAvgVolume());
            data.setTicker(ticker);
            data.setAvgVolume(polygonData.getAvgVolume());
            data.setFloatShares(polygonData.getFloatShares());

            yahooData = yahooFinanceClient.fetchFromYahoo(ticker);
            //data = new ExternalStockData(ticker, yahooData.getPrice(), yahooData.getChange(), yahooData.getChangeInPercent(), polygonData.getFloatShares(), polygonData.getAvgVolume());
            data.setPrice(yahooData.getPrice());
            data.setChange(yahooData.getChange());
            data.setChangeInPercent(yahooData.getChangeInPercent());
        } catch (IOException e) {
            System.err.println("Yahoo fallback also failed: " + e.getMessage());
            // Return minimal default to avoid null pointer downstream
            //data.setTicker(ticker);
            //data.setAvgVolume(0d);
            //data.setFloatShares(0d);
        }

        return data;
    }
}

