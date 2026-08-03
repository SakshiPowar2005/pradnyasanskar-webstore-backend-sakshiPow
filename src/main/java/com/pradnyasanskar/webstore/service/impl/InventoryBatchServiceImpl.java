package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.InventoryBatchRequestDTO;
import com.pradnyasanskar.webstore.dto.InventoryBatchResponseDTO;
import com.pradnyasanskar.webstore.entity.InventoryBatch;
import com.pradnyasanskar.webstore.entity.ProductVariant;
import com.pradnyasanskar.webstore.repository.InventoryBatchRepository;
import com.pradnyasanskar.webstore.repository.ProductVariantRepository;
import com.pradnyasanskar.webstore.service.InventoryBatchService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryBatchServiceImpl implements InventoryBatchService {

    private final InventoryBatchRepository inventoryBatchRepository;
    private final ProductVariantRepository productVariantRepository;

    public InventoryBatchServiceImpl(
            InventoryBatchRepository inventoryBatchRepository,
            ProductVariantRepository productVariantRepository) {

        this.inventoryBatchRepository = inventoryBatchRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public InventoryBatchResponseDTO createBatch(InventoryBatchRequestDTO request) {

        if (inventoryBatchRepository.existsByBatchNumber(request.getBatchNumber())) {
            throw new RuntimeException("Batch number already exists.");
        }

        ProductVariant variant = productVariantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Product Variant not found."));

        InventoryBatch batch = new InventoryBatch();

        batch.setProductVariant(variant);
        batch.setBatchNumber(request.getBatchNumber());
        batch.setManufacturingDate(request.getManufacturingDate());
        batch.setExpiryDate(request.getExpiryDate());
        batch.setPurchasePrice(request.getPurchasePrice());
        batch.setSellingPrice(request.getSellingPrice());
        batch.setQuantityInStock(request.getQuantityInStock());
        batch.setReservedQuantity(request.getReservedQuantity());
        batch.setReorderLevel(request.getReorderLevel());
        batch.setSupplierName(request.getSupplierName());
        batch.setReceivedDate(request.getReceivedDate());
        batch.setIsActive(true);

//        batch.setCreatedAt(LocalDateTime.now());
//        batch.setUpdatedAt(LocalDateTime.now());

        InventoryBatch savedBatch = inventoryBatchRepository.save(batch);

        return mapToResponse(savedBatch);
    }

    @Override
    public List<InventoryBatchResponseDTO> getAllBatches() {

        return inventoryBatchRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryBatchResponseDTO getBatchById(Long batchId) {

        InventoryBatch batch = inventoryBatchRepository.findByBatchIdAndIsActiveTrue(batchId)
                .orElseThrow(() -> new RuntimeException("Inventory Batch not found."));

        return mapToResponse(batch);
    }

    @Override
    public InventoryBatchResponseDTO getBatchByBatchNumber(String batchNumber) {

        InventoryBatch batch = inventoryBatchRepository.findByBatchNumberAndIsActiveTrue(batchNumber)
                .orElseThrow(() -> new RuntimeException("Inventory Batch not found."));

        return mapToResponse(batch);
    }

    @Override
    public List<InventoryBatchResponseDTO> getBatchesByVariant(Long variantId) {

        return inventoryBatchRepository.findByProductVariantVariantIdAndIsActiveTrue(variantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryBatchResponseDTO> getExpiredBatches() {

        return inventoryBatchRepository.findByExpiryDateBeforeAndIsActiveTrue(LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryBatchResponseDTO> getBatchesExpiringBefore(LocalDate date) {

        return inventoryBatchRepository.findByExpiryDateLessThanEqualAndIsActiveTrue(date)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryBatchResponseDTO> getLowStockBatches(Integer quantity) {

        return inventoryBatchRepository.findByQuantityInStockLessThanEqualAndIsActiveTrue(quantity)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryBatchResponseDTO updateBatch(Long batchId,
                                                 InventoryBatchRequestDTO request) {

        InventoryBatch batch =
                inventoryBatchRepository
                        .findByBatchIdAndIsActiveTrue(batchId)
                .orElseThrow(() -> new RuntimeException("Inventory Batch not found."));

        ProductVariant variant = productVariantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Product Variant not found."));

        batch.setProductVariant(variant);
        batch.setBatchNumber(request.getBatchNumber());
        batch.setManufacturingDate(request.getManufacturingDate());
        batch.setExpiryDate(request.getExpiryDate());
        batch.setPurchasePrice(request.getPurchasePrice());
        batch.setSellingPrice(request.getSellingPrice());
        batch.setQuantityInStock(request.getQuantityInStock());
        batch.setReservedQuantity(request.getReservedQuantity());
        batch.setReorderLevel(request.getReorderLevel());
        batch.setSupplierName(request.getSupplierName());
        batch.setReceivedDate(request.getReceivedDate());

//        batch.setUpdatedAt(LocalDateTime.now());

        InventoryBatch updatedBatch = inventoryBatchRepository.save(batch);

        return mapToResponse(updatedBatch);
    }

    @Override
    public void deleteBatch(Long batchId) {

        InventoryBatch batch = inventoryBatchRepository
                .findByBatchIdAndIsActiveTrue(batchId)
                .orElseThrow(() ->
                        new RuntimeException("Inventory Batch not found."));

        if (!batch.getIsActive()) {
            throw new RuntimeException("Inventory Batch already deleted.");
        }

        batch.setIsActive(false);

        inventoryBatchRepository.save(batch);
    }
    private InventoryBatchResponseDTO mapToResponse(InventoryBatch batch) {

        InventoryBatchResponseDTO response = new InventoryBatchResponseDTO();

        response.setBatchId(batch.getBatchId());

        response.setVariantId(batch.getProductVariant().getVariantId());

        response.setProductName(
                batch.getProductVariant()
                        .getProduct()
                        .getProductName());

        response.setVariantName(
                batch.getProductVariant()
                        .getVariantName());

        response.setBatchNumber(batch.getBatchNumber());
        response.setManufacturingDate(batch.getManufacturingDate());
        response.setExpiryDate(batch.getExpiryDate());
        response.setPurchasePrice(batch.getPurchasePrice());
        response.setSellingPrice(batch.getSellingPrice());
        response.setQuantityInStock(batch.getQuantityInStock());
        response.setReservedQuantity(batch.getReservedQuantity());
        response.setReorderLevel(batch.getReorderLevel());
        response.setSupplierName(batch.getSupplierName());
        response.setReceivedDate(batch.getReceivedDate());

        response.setIsActive(batch.getIsActive());
        return response;
    }
}