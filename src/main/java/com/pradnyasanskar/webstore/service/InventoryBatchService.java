package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.InventoryBatchRequestDTO;
import com.pradnyasanskar.webstore.dto.InventoryBatchResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface InventoryBatchService {

    // Create new inventory batch
    InventoryBatchResponseDTO createBatch(InventoryBatchRequestDTO request);

    // Get all inventory batches
    List<InventoryBatchResponseDTO> getAllBatches();

    // Get batch by ID
    InventoryBatchResponseDTO getBatchById(Long batchId);

    // Get batch by batch number
    InventoryBatchResponseDTO getBatchByBatchNumber(String batchNumber);

    // Get all batches of a product variant
    List<InventoryBatchResponseDTO> getBatchesByVariant(Long variantId);

    // Get expired batches
    List<InventoryBatchResponseDTO> getExpiredBatches();

    // Get batches expiring before a date
    List<InventoryBatchResponseDTO> getBatchesExpiringBefore(LocalDate date);

    // Get low stock batches
    List<InventoryBatchResponseDTO> getLowStockBatches(Integer quantity);

    // Update batch
    InventoryBatchResponseDTO updateBatch(Long batchId,
                                          InventoryBatchRequestDTO request);

    // Delete batch
    void deleteBatch(Long batchId);
}