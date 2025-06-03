package com.hmack101.screener.dto;


public class ExternalStockData {
    private String ticker;
    private Double price;
    private Double change;
    private Double changeInPercent;
    private Double floatShares;
    private Double avgVolume;

    public ExternalStockData() {

    }

    public ExternalStockData(String ticker, Double price, Double change, Double changeInPercent, Double floatShares, Double avgVolume) {
        this.ticker = ticker;
        this.price = price;
        this.change = change;
        this.changeInPercent = changeInPercent;
        this.floatShares = floatShares;
        this.avgVolume = avgVolume;
    }


    // Getters and Setters
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public Double getPrice() {
        return price;
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

    public Double getFloatShares() { return floatShares; }
    public void setFloatShares(Double floatShares) { this.floatShares = floatShares; }

    public Double getAvgVolume() { return avgVolume; }
    public void setAvgVolume(Double avgVolume) { this.avgVolume = avgVolume; }
}
