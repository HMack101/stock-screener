package com.hmack101.screener.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigInteger;


@Entity
@Table(name = "previous_day_data")
public class PreviousDayData {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column(unique = true)
    private String ticker;

    private Double volume;

    private Double volumeWeightedAveragePrice;

    private Double openPrice;
    private Double closePrice;
    private Double highPrice;
    private Double lowPrice;
    private long numberOfTransactions;


    public PreviousDayData() {}

    public PreviousDayData(String ticker, Double volume, Double volumeWeightedAveragePrice, Double openPrice, Double closePrice, Double highPrice, Double lowPrice, long numberOfTransactions) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
