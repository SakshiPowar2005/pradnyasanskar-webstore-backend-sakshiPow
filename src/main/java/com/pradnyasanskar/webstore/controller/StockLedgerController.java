package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.StockLedgerRequestDTO;
import com.pradnyasanskar.webstore.dto.StockLedgerResponseDTO;
import com.pradnyasanskar.webstore.entity.StockTransactionType;
import com.pradnyasanskar.webstore.service.StockLedgerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-ledger")
public class StockLedgerController {

    private final StockLedgerService stockLedgerService;

    public StockLedgerController(StockLedgerService stockLedgerService) {
        this.stockLedgerService = stockLedgerService;
    }

    @PostMapping
    public StockLedgerResponseDTO createTransaction(
            @RequestBody StockLedgerRequestDTO request) {

        return stockLedgerService.createTransaction(request);
    }

    @GetMapping
    public List<StockLedgerResponseDTO> getAllTransactions() {

        return stockLedgerService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public StockLedgerResponseDTO getTransactionById(
            @PathVariable Long id) {

        return stockLedgerService.getTransactionById(id);
    }

    @GetMapping("/batch/{batchId}")
    public List<StockLedgerResponseDTO> getTransactionsByBatch(
            @PathVariable Long batchId) {

        return stockLedgerService.getTransactionsByBatch(batchId);
    }

    @GetMapping("/type/{type}")
    public List<StockLedgerResponseDTO> getTransactionsByType(
            @PathVariable StockTransactionType type) {

        return stockLedgerService.getTransactionsByType(type);
    }
}