package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.InvoiceRequestDTO;
import com.pradnyasanskar.webstore.dto.InvoiceResponseDTO;
import com.pradnyasanskar.webstore.dto.UpdateInvoiceStatusDTO;
import com.pradnyasanskar.webstore.entity.InvoiceStatus;
import com.pradnyasanskar.webstore.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // =====================================================
    // CREATE INVOICE
    // =====================================================

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(
            @RequestBody InvoiceRequestDTO request) {

        return new ResponseEntity<>(
                invoiceService.createInvoice(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // GET ALL INVOICES
    // =====================================================

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {

        return ResponseEntity.ok(
                invoiceService.getAllInvoices());
    }

    // =====================================================
    // GET INVOICE BY ID
    // =====================================================

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(
            @PathVariable Long invoiceId) {

        return ResponseEntity.ok(
                invoiceService.getInvoiceById(invoiceId));
    }

    // =====================================================
    // GET INVOICE BY NUMBER
    // =====================================================

    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByNumber(
            @PathVariable String invoiceNumber) {

        return ResponseEntity.ok(
                invoiceService.getInvoiceByNumber(invoiceNumber));
    }

    // =====================================================
    // GET INVOICE BY ORDER
    // =====================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                invoiceService.getInvoiceByOrder(orderId));
    }

    // =====================================================
    // GET INVOICES BY STATUS
    // =====================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByStatus(
            @PathVariable InvoiceStatus status) {

        return ResponseEntity.ok(
                invoiceService.getInvoicesByStatus(status));
    }

    // =====================================================
    // UPDATE INVOICE STATUS
    // =====================================================

    @PutMapping("/{invoiceId}/status")
    public ResponseEntity<InvoiceResponseDTO> updateInvoiceStatus(
            @PathVariable Long invoiceId,
            @RequestBody UpdateInvoiceStatusDTO request) {

        return ResponseEntity.ok(
                invoiceService.updateInvoiceStatus(
                        invoiceId,
                        request.getInvoiceStatus()));
    }

    // =====================================================
    // DELETE INVOICE
    // =====================================================

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<String> deleteInvoice(
            @PathVariable Long invoiceId) {

        invoiceService.deleteInvoice(invoiceId);

        return ResponseEntity.ok(
                "Invoice deleted successfully.");
    }
}