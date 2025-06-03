package com.hmack101.screener.dto;

import com.hmack101.screener.model.Stock;

public class StockDTO {
    private Integer id;
    private String ticker;
    private Double price;
    private Double change;
    private Double changeInPercent;
    private Double floatShares;
    private Double avgVolume;

    public StockDTO() {}

    public StockDTO(String ticker, Double price, Double change, Double changeInPercent, Double floatShares, Double avgVolume) {
        this.ticker = ticker;
        this.price = price;
        this.change = change;
        this.changeInPercent = changeInPercent;
        this.floatShares = floatShares;
        this.avgVolume = avgVolume;
    }


    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public static StockDTO fromEntity(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setPrice(stock.getPrice());
        dto.setChange(stock.getChange());
        dto.setChangeInPercent(stock.getChangeInPercent());
        dto.setTicker(stock.getTicker());
        dto.setFloatShares(stock.getFloatShares());
        dto.setAvgVolume(stock.getAvgVolume());
        return dto;
    }

    public static StockDTO toDTO(Stock stock) {
        return new StockDTO(
                stock.getTicker(),
                stock.getPrice(),
                stock.getChange(),
                stock.getChangeInPercent(),
                stock.getFloatShares(),
                stock.getAvgVolume()
        );
    }

    public static Stock toEntity(StockDTO dto) {
        return new Stock(dto.getTicker(),
                dto.getPrice(),
                dto.getChange(),
                dto.getChangeInPercent(),
                dto.getFloatShares(),
                dto.getAvgVolume());
    }
}
