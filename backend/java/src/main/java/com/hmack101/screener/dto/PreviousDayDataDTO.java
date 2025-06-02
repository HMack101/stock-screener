package com.hmack101.screener.dto;

import com.hmack101.screener.model.PreviousDayData;

public class PreviousDayDataDTO {

    private String ticker;
    private Double volume;
    private Double volumeWeightedAveragePrice;
    private Double openPrice;
    private Double closePrice;
    private Double highPrice;
    private Double lowPrice;
    private long numberOfTransactions;

    public PreviousDayDataDTO() {}

    public PreviousDayDataDTO(
            String ticker,
            Double volume,
            Double volumeWeightedAveragePrice,
            Double openPrice,
            Double closePrice,
            Double highPrice,
            Double lowPrice,
            long numberOfTransactions) {
        this.ticker = ticker;
        this.volume = volume;
        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.numberOfTransactions = numberOfTransactions;
    }

    // Getters and Setters

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }

    public void setVolumeWeightedAveragePrice(Double volumeWeightedAveragePrice) {
        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(long numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    // Mapper: DTO -> Entity
    public PreviousDayData toEntity() {
        return new PreviousDayData(
                this.ticker,
                this.volume,
                this.volumeWeightedAveragePrice,
                this.openPrice,
                this.closePrice,
                this.highPrice,
                this.lowPrice,
                this.numberOfTransactions
        );
    }

    // Mapper: Entity -> DTO
    public static PreviousDayDataDTO fromEntity(PreviousDayData entity) {
        return new PreviousDayDataDTO(
                entity.getTicker(),
                entity.getVolume(),
                entity.getVolumeWeightedAveragePrice(),
                entity.getOpenPrice(),
                entity.getClosePrice(),
                entity.getHighPrice(),
                entity.getLowPrice(),
                entity.getNumberOfTransactions()
        );
    }
}