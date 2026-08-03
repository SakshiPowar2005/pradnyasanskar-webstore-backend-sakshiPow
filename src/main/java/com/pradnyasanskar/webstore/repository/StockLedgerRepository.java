package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.StockLedger;
import com.pradnyasanskar.webstore.entity.StockTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockLedgerRepository extends JpaRepository<StockLedger, Long> {

    List<StockLedger> findByInventoryBatchBatchId(Long batchId);

    List<StockLedger> findByTransactionType(StockTransactionType transactionType);

    List<StockLedger> findByReferenceTypeAndReferenceId(
            String referenceType,
            Long referenceId
    );

}