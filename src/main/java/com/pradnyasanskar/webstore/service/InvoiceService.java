package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.InvoiceRequestDTO;
import com.pradnyasanskar.webstore.dto.InvoiceResponseDTO;
import com.pradnyasanskar.webstore.entity.InvoiceStatus;

import java.util.List;

public interface InvoiceService {

    // Create Invoice
    InvoiceResponseDTO createInvoice(InvoiceRequestDTO request);

    // Get All Invoices
    List<InvoiceResponseDTO> getAllInvoices();

    // Get Invoice By ID
    InvoiceResponseDTO getInvoiceById(Long invoiceId);

    // Get Invoice By Invoice Number
    InvoiceResponseDTO getInvoiceByNumber(String invoiceNumber);

    // Get Invoice By Order
    InvoiceResponseDTO getInvoiceByOrder(Long orderId);

    // Get Invoices By Status
    List<InvoiceResponseDTO> getInvoicesByStatus(InvoiceStatus invoiceStatus);

    // Update Invoice Status
    InvoiceResponseDTO updateInvoiceStatus(
            Long invoiceId,
            InvoiceStatus invoiceStatus);

    // Delete Invoice
    void deleteInvoice(Long invoiceId);

    //invoice by user
    List<InvoiceResponseDTO> getInvoicesByUser(Long userId);
}