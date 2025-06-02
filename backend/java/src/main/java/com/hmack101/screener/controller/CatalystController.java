package com.hmack101.screener.controller;


import com.hmack101.screener.dto.CatalystDTO;
import com.hmack101.screener.model.Catalyst;
import com.hmack101.screener.service.CatalystService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalysts")
public class CatalystController {

    @Autowired
    private CatalystService catalystService;

    @GetMapping
    public List<CatalystDTO> getAllCatalysts() {
        return catalystService.getAllCatalysts()
                .stream()
                .map(CatalystDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalystDTO> getCatalystById(@PathVariable Integer id) {
        Optional<Catalyst> catalystOpt = catalystService.getCatalystById(id);
        return catalystOpt.map(c -> ResponseEntity.ok(CatalystDTO.toDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ticker/{ticker}")
    public List<CatalystDTO> getCatalystsByTicker(@PathVariable String ticker) {
        return catalystService.getCatalystsByTicker(ticker)
                .stream()
                .map(CatalystDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/high-impact")
    public List<CatalystDTO> getHighImpactCatalysts() {
        return catalystService.getHighImpactCatalysts()
                .stream()
                .map(CatalystDTO::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CatalystDTO createCatalyst(@RequestBody CatalystDTO dto) {
        Catalyst saved = catalystService.createOrUpdateCatalyst(CatalystDTO.toEntity(dto));
        return CatalystDTO.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalystDTO> updateCatalyst(@PathVariable Integer id, @RequestBody CatalystDTO dto) {
        if (!catalystService.getCatalystById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Catalyst updated = catalystService.createOrUpdateCatalyst(CatalystDTO.toEntity(dto));
        return ResponseEntity.ok(CatalystDTO.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalyst(@PathVariable Integer id) {
        if (!catalystService.getCatalystById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        catalystService.deleteCatalyst(id);
        return ResponseEntity.noContent().build();
    }
}
