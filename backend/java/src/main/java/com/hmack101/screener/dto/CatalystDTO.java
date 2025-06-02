package com.hmack101.screener.dto;


import java.time.LocalDateTime;

public class CatalystDTO {
    private Integer id;
    private String ticker;
    private String type;
    private String title;
    private String url;
    private LocalDateTime timestamp;
    private Boolean isHighImpact;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Boolean getIsHighImpact() { return isHighImpact; }
    public void setIsHighImpact(Boolean isHighImpact) { this.isHighImpact = isHighImpact; }
}
