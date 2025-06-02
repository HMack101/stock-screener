package com.hmack101.screener.controller;

import com.hmack101.screener.dto.StockDTO;
import com.hmack101.screener.model.Stock;
import com.hmack101.screener.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    // Add or update stock by ticker via POST
    @PostMapping("/addOrUpdate/{ticker}")
    public ResponseEntity<StockDTO> addOrUpdateStock(@PathVariable String ticker) {
        Stock stock = stockService.addOrUpdateStock(ticker);

        StockDTO dto = StockDTO.fromEntity(stock);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
       return ResponseEntity.ok(stockService.listStocks());
    }


    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Integer id) {
        Optional<Stock> stockOpt = stockService.getStockById(id);
        return stockOpt.map(stock -> ResponseEntity.ok(StockDTO.toDTO(stock)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<StockDTO> getStockByTicker(@PathVariable String ticker) {
        Optional<Stock> stockOpt = stockService.getStockByTicker(ticker);
        return stockOpt.map(stock -> ResponseEntity.ok(StockDTO.toDTO(stock)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StockDTO createStock(@RequestBody StockDTO dto) {
        Stock saved = stockService.createOrUpdateStock(StockDTO.toEntity(dto));
        return StockDTO.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Integer id, @RequestBody StockDTO dto) {
        if (!stockService.getStockById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Stock updated = stockService.createOrUpdateStock(StockDTO.toEntity(dto));
        return ResponseEntity.ok(StockDTO.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        if (!stockService.getStockById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
