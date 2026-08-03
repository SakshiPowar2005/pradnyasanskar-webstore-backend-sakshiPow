package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.StockTransactionType;

import java.time.LocalDateTime;

public class StockLedgerResponseDTO {

    private Long stockLedgerId;

    private Long batchId;

    private String batchNumber;

    private StockTransactionType transactionType;

    private Integer quantity;

    private Integer balanceAfterTransaction;

    private String referenceType;

    private Long referenceId;

    private String remarks;

    private Long createdBy;

    private LocalDateTime transactionTime;

    public StockLedgerResponseDTO() {
    }

    public Long getStockLedgerId() {
        return stockLedgerId;
    }

    public void setStockLedgerId(Long stockLedgerId) {
        this.stockLedgerId = stockLedgerId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}