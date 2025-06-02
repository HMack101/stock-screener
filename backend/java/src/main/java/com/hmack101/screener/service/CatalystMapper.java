package com.hmack101.screener.service;

import com.hmack101.screener.dto.CatalystDTO;
import com.hmack101.screener.model.Catalyst;

public class CatalystMapper {
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
