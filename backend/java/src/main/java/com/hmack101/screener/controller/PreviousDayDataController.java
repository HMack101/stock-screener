package com.hmack101.screener.controller;

import com.hmack101.screener.dto.PreviousDayDataDTO;
import com.hmack101.screener.model.PreviousDayData;
import com.hmack101.screener.service.PreviousDayDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/previous-day-data")
public class PreviousDayDataController {

    private final PreviousDayDataService service;

    @Autowired
    public PreviousDayDataController(PreviousDayDataService service) {
        this.service = service;
    }

    @GetMapping
    public List<PreviousDayDataDTO> getAll() {
        return service.getAllData().stream()
                .map(PreviousDayDataDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreviousDayDataDTO> getById(@PathVariable Integer id) {
        Optional<PreviousDayData> data = service.getById(id);
        return data.map(value -> ResponseEntity.ok(PreviousDayDataDTO.fromEntity(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<PreviousDayDataDTO> getByTicker(@PathVariable String ticker) {
        Optional<PreviousDayData> data = service.getByTicker(ticker);
        return data.map(value -> ResponseEntity.ok(PreviousDayDataDTO.fromEntity(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PreviousDayDataDTO> create(@RequestBody PreviousDayDataDTO dto) {
        PreviousDayData saved = service.save(dto.toEntity());
        return ResponseEntity.ok(PreviousDayDataDTO.fromEntity(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreviousDayDataDTO> update(
            @PathVariable Integer id,
            @RequestBody PreviousDayDataDTO dto
    ) {
        Optional<PreviousDayData> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PreviousDayData entityToUpdate = dto.toEntity();
        entityToUpdate.setId(id); // Ensure ID stays the same
        PreviousDayData updated = service.save(entityToUpdate);

        return ResponseEntity.ok(PreviousDayDataDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<PreviousDayData> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
