package com.hmack101.screener.service;

import com.hmack101.screener.dto.StockDTO;
import com.hmack101.screener.model.Stock;

public class StockMapper {
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
