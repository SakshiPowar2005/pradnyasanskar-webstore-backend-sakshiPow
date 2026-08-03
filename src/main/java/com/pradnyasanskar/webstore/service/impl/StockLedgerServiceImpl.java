package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.StockLedgerRequestDTO;
import com.pradnyasanskar.webstore.dto.StockLedgerResponseDTO;
import com.pradnyasanskar.webstore.entity.InventoryBatch;
import com.pradnyasanskar.webstore.entity.StockLedger;
import com.pradnyasanskar.webstore.entity.StockTransactionType;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.InventoryBatchRepository;
import com.pradnyasanskar.webstore.repository.StockLedgerRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.StockLedgerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockLedgerServiceImpl implements StockLedgerService {

    private final StockLedgerRepository stockLedgerRepository;
    private final InventoryBatchRepository inventoryBatchRepository;
    private final UserRepository userRepository;

    public StockLedgerServiceImpl(
            StockLedgerRepository stockLedgerRepository,
            InventoryBatchRepository inventoryBatchRepository,
            UserRepository userRepository) {

        this.stockLedgerRepository = stockLedgerRepository;
        this.inventoryBatchRepository = inventoryBatchRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StockLedgerResponseDTO createTransaction(StockLedgerRequestDTO request) {

        InventoryBatch batch = inventoryBatchRepository.findById(request.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found."));

        User user = null;

        if (request.getCreatedBy() != null) {
            user = userRepository.findById(request.getCreatedBy())
                    .orElseThrow(() -> new RuntimeException("User not found."));
        }

        int currentStock = batch.getQuantityInStock();

        switch (request.getTransactionType()) {

            case PURCHASE:
            case RETURN:
                currentStock += request.getQuantity();
                break;

            case SALE:
            case DAMAGED:
            case EXPIRED:
                if (currentStock < request.getQuantity()) {
                    throw new RuntimeException("Insufficient stock.");
                }
                currentStock -= request.getQuantity();
                break;

            case ADJUSTMENT:
                currentStock += request.getQuantity();
                break;

            case CANCELLED:
                currentStock += request.getQuantity();
                break;
        }

        batch.setQuantityInStock(currentStock);

        inventoryBatchRepository.save(batch);

        StockLedger ledger = new StockLedger();

        ledger.setInventoryBatch(batch);
        ledger.setTransactionType(request.getTransactionType());
        ledger.setQuantity(request.getQuantity());
        ledger.setBalanceAfterTransaction(currentStock);
        ledger.setReferenceType(request.getReferenceType());
        ledger.setReferenceId(request.getReferenceId());
        ledger.setRemarks(request.getRemarks());
        ledger.setCreatedBy(user);

        StockLedger saved = stockLedgerRepository.save(ledger);

        return mapToResponse(saved);
    }

    @Override
    public List<StockLedgerResponseDTO> getAllTransactions() {

        return stockLedgerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StockLedgerResponseDTO getTransactionById(Long id) {

        StockLedger ledger = stockLedgerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found."));

        return mapToResponse(ledger);
    }

    @Override
    public List<StockLedgerResponseDTO> getTransactionsByBatch(Long batchId) {

        return stockLedgerRepository.findByInventoryBatchBatchId(batchId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockLedgerResponseDTO> getTransactionsByType(StockTransactionType transactionType) {

        return stockLedgerRepository.findByTransactionType(transactionType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private StockLedgerResponseDTO mapToResponse(StockLedger ledger) {

        StockLedgerResponseDTO response = new StockLedgerResponseDTO();

        response.setStockLedgerId(ledger.getStockLedgerId());

        response.setBatchId(
                ledger.getInventoryBatch().getBatchId());

        response.setBatchNumber(
                ledger.getInventoryBatch().getBatchNumber());

        response.setTransactionType(
                ledger.getTransactionType());

        response.setQuantity(
                ledger.getQuantity());

        response.setBalanceAfterTransaction(
                ledger.getBalanceAfterTransaction());

        response.setReferenceType(
                ledger.getReferenceType());

        response.setReferenceId(
                ledger.getReferenceId());

        response.setRemarks(
                ledger.getRemarks());

        if (ledger.getCreatedBy() != null)
            response.setCreatedBy(
                    ledger.getCreatedBy().getUserId());

        response.setTransactionTime(
                ledger.getTransactionTime());

        return response;
    }

}