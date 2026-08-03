package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {

    // Find batch by batch number
    Optional<InventoryBatch> findByBatchNumber(String batchNumber);

    // Check if batch number already exists
    boolean existsByBatchNumber(String batchNumber);

    // NEW
    List<InventoryBatch> findByIsActiveTrue();

    // Get all batches of a product variant
    List<InventoryBatch> findByProductVariantVariantId(Long variantId);

    // Get expired batches
    List<InventoryBatch> findByExpiryDateBefore(LocalDate date);

    // Get batches expiring before a specific date
    List<InventoryBatch> findByExpiryDateLessThanEqual(LocalDate date);

    // Low stock batches
    List<InventoryBatch> findByQuantityInStockLessThanEqual(Integer quantity);

    // Get batches by supplier
    List<InventoryBatch> findBySupplierName(String supplierName);

    List<InventoryBatch> findByProductVariantVariantIdOrderByExpiryDateAsc(Long variantId);

    // Soft Delete Methods
    Optional<InventoryBatch> findByBatchIdAndIsActiveTrue(Long batchId);

    Optional<InventoryBatch> findByBatchNumberAndIsActiveTrue(String batchNumber);

    List<InventoryBatch> findByProductVariantVariantIdAndIsActiveTrue(Long variantId);

    List<InventoryBatch> findByExpiryDateBeforeAndIsActiveTrue(LocalDate date);

    List<InventoryBatch> findByExpiryDateLessThanEqualAndIsActiveTrue(LocalDate date);

    List<InventoryBatch> findByQuantityInStockLessThanEqualAndIsActiveTrue(Integer quantity);

}