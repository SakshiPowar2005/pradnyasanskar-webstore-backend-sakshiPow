package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.StockLedgerRequestDTO;
import com.pradnyasanskar.webstore.dto.StockLedgerResponseDTO;
import com.pradnyasanskar.webstore.entity.StockTransactionType;

import java.util.List;

public interface StockLedgerService {

    StockLedgerResponseDTO createTransaction(StockLedgerRequestDTO request);

    List<StockLedgerResponseDTO> getAllTransactions();

    StockLedgerResponseDTO getTransactionById(Long id);

    List<StockLedgerResponseDTO> getTransactionsByBatch(Long batchId);

    List<StockLedgerResponseDTO> getTransactionsByType(StockTransactionType transactionType);

}