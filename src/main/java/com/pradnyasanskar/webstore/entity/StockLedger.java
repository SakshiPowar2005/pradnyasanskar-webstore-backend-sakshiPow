package com.pradnyasanskar.webstore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;




@Entity
@Table(name = "stock_ledger")
public class StockLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_ledger_id")
    private Long stockLedgerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id", nullable = false)
    private InventoryBatch inventoryBatch;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "transaction_type")
    private StockTransactionType transactionType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "balance_after_transaction", nullable = false)
    private Integer balanceAfterTransaction;

    @Column(name = "reference_type")
    private String referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "transaction_time", updatable = false)
    private LocalDateTime transactionTime;

    public StockLedger() {
    }

    @PrePersist
    public void prePersist() {
        transactionTime = LocalDateTime.now();
    }

    // ===================== Getters & Setters =====================

    public Long getStockLedgerId() {
        return stockLedgerId;
    }

    public void setStockLedgerId(Long stockLedgerId) {
        this.stockLedgerId = stockLedgerId;
    }

    public InventoryBatch getInventoryBatch() {
        return inventoryBatch;
    }

    public void setInventoryBatch(InventoryBatch inventoryBatch) {
        this.inventoryBatch = inventoryBatch;
    }

    public StockTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(StockTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(Integer balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
}