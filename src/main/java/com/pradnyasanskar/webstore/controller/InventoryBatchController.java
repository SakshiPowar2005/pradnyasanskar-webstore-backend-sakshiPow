package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.InventoryBatchRequestDTO;
import com.pradnyasanskar.webstore.dto.InventoryBatchResponseDTO;
import com.pradnyasanskar.webstore.service.InventoryBatchService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inventory-batches")
public class InventoryBatchController {

    private final InventoryBatchService inventoryBatchService;

    public InventoryBatchController(InventoryBatchService inventoryBatchService) {
        this.inventoryBatchService = inventoryBatchService;
    }

    // Create Inventory Batch
    @PostMapping
    public InventoryBatchResponseDTO createBatch(
            @RequestBody InventoryBatchRequestDTO request) {

        return inventoryBatchService.createBatch(request);
    }

    // Get All Inventory Batches
    @GetMapping
    public List<InventoryBatchResponseDTO> getAllBatches() {

        return inventoryBatchService.getAllBatches();
    }

    // Get Inventory Batch By ID
    @GetMapping("/{id}")
    public InventoryBatchResponseDTO getBatchById(
            @PathVariable Long id) {

        return inventoryBatchService.getBatchById(id);
    }

    // Get Batch By Batch Number
    @GetMapping("/batch/{batchNumber}")
    public InventoryBatchResponseDTO getBatchByBatchNumber(
            @PathVariable String batchNumber) {

        return inventoryBatchService.getBatchByBatchNumber(batchNumber);
    }

    // Get Batches By Variant
    @GetMapping("/variant/{variantId}")
    public List<InventoryBatchResponseDTO> getBatchesByVariant(
            @PathVariable Long variantId) {

        return inventoryBatchService.getBatchesByVariant(variantId);
    }

    // Get Expired Batches
    @GetMapping("/expired")
    public List<InventoryBatchResponseDTO> getExpiredBatches() {

        return inventoryBatchService.getExpiredBatches();
    }

    // Get Batches Expiring Before Date
    @GetMapping("/expiring")
    public List<InventoryBatchResponseDTO> getExpiringBatches(
            @RequestParam LocalDate date) {

        return inventoryBatchService.getBatchesExpiringBefore(date);
    }

    // Get Low Stock Batches
    @GetMapping("/low-stock")
    public List<InventoryBatchResponseDTO> getLowStockBatches(
            @RequestParam Integer quantity) {

        return inventoryBatchService.getLowStockBatches(quantity);
    }

    // Update Batch
    @PutMapping("/{id}")
    public InventoryBatchResponseDTO updateBatch(
            @PathVariable Long id,
            @RequestBody InventoryBatchRequestDTO request) {

        return inventoryBatchService.updateBatch(id, request);
    }

    // Delete Batch
    @DeleteMapping("/{id}")
    public String deleteBatch(
            @PathVariable Long id) {

        inventoryBatchService.deleteBatch(id);

        return "Inventory Batch deleted successfully.";
    }

}