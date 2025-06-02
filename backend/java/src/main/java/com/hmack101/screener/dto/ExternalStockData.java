package com.hmack101.screener.dto;


public class ExternalStockData {
    private String ticker;
    private Double floatShares;
    private Double avgVolume;

    // Getters and Setters
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public Double getFloatShares() { return floatShares; }
    public void setFloatShares(Double floatShares) { this.floatShares = floatShares; }

    public Double getAvgVolume() { return avgVolume; }
    public void setAvgVolume(Double avgVolume) { this.avgVolume = avgVolume; }
}
