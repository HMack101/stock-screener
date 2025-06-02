package com.hmack101.screener.dto;

import com.hmack101.screener.model.Stock;

public class StockDTO {
    private Integer id;
    private String ticker;
    private Double floatShares;
    private Double avgVolume;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public Double getFloatShares() { return floatShares; }
    public void setFloatShares(Double floatShares) { this.floatShares = floatShares; }

    public Double getAvgVolume() { return avgVolume; }
    public void setAvgVolume(Double avgVolume) { this.avgVolume = avgVolume; }

    public static StockDTO fromEntity(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setFloatShares(stock.getFloatShares());
        dto.setAvgVolume(stock.getAvgVolume());
        return dto;
    }

    public static StockDTO toDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setFloatShares(stock.getFloatShares());
        dto.setAvgVolume(stock.getAvgVolume());
        return dto;
    }

    public static Stock toEntity(StockDTO dto) {
        Stock stock = new Stock();
        stock.setId(dto.getId());
        stock.setTicker(dto.getTicker());
        stock.setFloatShares(dto.getFloatShares());
        stock.setAvgVolume(dto.getAvgVolume());
        return stock;
    }
}
