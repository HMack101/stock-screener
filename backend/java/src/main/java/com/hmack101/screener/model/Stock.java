package com.hmack101.screener.model;

// Stock.java
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column(unique = true)
    private String ticker;

    private Double price;

    private Double change;
    private Double changeInPercent;

    private Double floatShares;

    private Double avgVolume;

    public Stock() {}

    public Stock(String ticker, Double price, Double change, Double changeInPercent, Double floatShares, Double avg) {
        this.ticker = ticker;
        this.price = price;
        this.change = change;
        this.changeInPercent = changeInPercent;
        this.floatShares = floatShares;
        this.avgVolume = avg;
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



    public Double getFloatShares() {
        return floatShares;
    }

    public void setFloatShares(Double floatShares) {
        this.floatShares = floatShares;
    }

    public Double getAvgVolume() {
        return avgVolume;
    }

    public void setAvgVolume(Double avgVolume) {
        this.avgVolume = avgVolume;
    }
}