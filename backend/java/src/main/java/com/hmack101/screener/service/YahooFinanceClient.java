package com.hmack101.screener.service;

import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;

@Service
public class YahooFinanceClient {

    public class YahooFinanceData {

        private String ticker;

        private Double price;

        private Double change;
        private Double changeInPercent;


        public YahooFinanceData() {}

        public YahooFinanceData(String ticker, Double price, Double change, Double changeInPercent) {
            this.ticker = ticker;
            this.price = price;
            this.change = change;
            this.changeInPercent = changeInPercent;
        }

        // Getters and Setters
        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public Double getPrice() {
            return this.price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getChange() {
            return change;
        }

        public void setChange(Double change) {
            this.change = change;
        }

        public Double getChangeInPercent() {
            return changeInPercent;
        }


        public void setChangeInPercent(Double changeInPercent) {
            this.changeInPercent = changeInPercent;
        }


    }

    public YahooFinanceData fetchFromYahoo(String ticker) throws IOException {
        System.setProperty("http.agent", "Mozilla/5.0");
        Stock yahooStock = YahooFinance.get(ticker);
        YahooFinanceData data = new YahooFinanceData();
        data.setTicker(ticker);

        if (yahooStock != null && yahooStock.getQuote() != null) {
            System.out.println("yahoo stock for ticker: " + ticker + " - " + yahooStock.getQuote().toString());
            data.setPrice(yahooStock.getQuote().getPrice() != null ? yahooStock.getQuote().getPrice().doubleValue() : 0d);
            data.setChange(yahooStock.getQuote().getChange() != null ? yahooStock.getQuote().getChange().doubleValue() : 0d);
            data.setChangeInPercent(yahooStock.getQuote().getChangeInPercent() != null ? yahooStock.getQuote().getChangeInPercent().doubleValue() : 0d);
        } else {
            data.setPrice(0d);
            data.setChange(0d);
            data.setChangeInPercent(0d);
        }

        return data;
    }
}
