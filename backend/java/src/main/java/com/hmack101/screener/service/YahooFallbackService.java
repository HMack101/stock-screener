package com.hmack101.screener.service;

import com.hmack101.screener.dto.ExternalStockData;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;

@Service
public class YahooFallbackService {

    public ExternalStockData fetchFromYahoo(String ticker) throws IOException {
        Stock yahooStock = YahooFinance.get(ticker);
        ExternalStockData data = new ExternalStockData();
        data.setTicker(ticker);

        if (yahooStock != null && yahooStock.getQuote() != null) {
            Long avgVolume = yahooStock.getQuote().getAvgVolume();
            data.setAvgVolume(avgVolume != null ? avgVolume.floatValue() : 0d);
        } else {
            data.setAvgVolume(0d);
        }

        // Yahoo doesn't provide float shares, fallback to 0
        data.setFloatShares(0d);
        return data;
    }
}
