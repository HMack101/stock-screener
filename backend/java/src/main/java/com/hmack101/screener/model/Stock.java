package com.hmack101.screener.model;

// Stock.java
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column(unique = true)
    private String ticker;

    private Double floatShares;

    private Double avgVolume;

    public Stock() {}

    public Stock(String ticker, Double floatShares, Double avg) {
        this.ticker = ticker;
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