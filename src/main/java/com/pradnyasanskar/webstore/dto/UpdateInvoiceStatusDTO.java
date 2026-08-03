package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.InvoiceStatus;

public class UpdateInvoiceStatusDTO {

    private InvoiceStatus invoiceStatus;

    public UpdateInvoiceStatusDTO() {
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}