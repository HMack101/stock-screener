package com.hmack101.screener.dto;


import com.hmack101.screener.model.Catalyst;

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

    public static CatalystDTO toDTO(Catalyst catalyst) {
        CatalystDTO dto = new CatalystDTO();
        dto.setId(catalyst.getId());
        dto.setTicker(catalyst.getTicker());
        dto.setType(catalyst.getType());
        dto.setTitle(catalyst.getTitle());
        dto.setUrl(catalyst.getUrl());
        dto.setTimestamp(catalyst.getTimestamp());
        dto.setIsHighImpact(catalyst.getIsHighImpact());
        return dto;
    }

    public static Catalyst toEntity(CatalystDTO dto) {
        Catalyst catalyst = new Catalyst();
        catalyst.setId(dto.getId());
        catalyst.setTicker(dto.getTicker());
        catalyst.setType(dto.getType());
        catalyst.setTitle(dto.getTitle());
        catalyst.setUrl(dto.getUrl());
        catalyst.setTimestamp(dto.getTimestamp());
        catalyst.setIsHighImpact(dto.getIsHighImpact());
        return catalyst;
    }
}
