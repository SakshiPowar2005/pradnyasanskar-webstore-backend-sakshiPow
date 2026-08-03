package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.InvoiceRequestDTO;
import com.pradnyasanskar.webstore.dto.InvoiceResponseDTO;
import com.pradnyasanskar.webstore.entity.Invoice;
import com.pradnyasanskar.webstore.entity.InvoiceStatus;
import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.repository.InvoiceRepository;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import com.pradnyasanskar.webstore.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            OrderRepository orderRepository) {

        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
    }
    // =====================================================
    // CREATE INVOICE
    // =====================================================

    @Override
    public InvoiceResponseDTO createInvoice(
            InvoiceRequestDTO request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        if (invoiceRepository.existsByOrderOrderId(order.getOrderId())) {
            throw new RuntimeException(
                    "Invoice already exists for this order.");
        }

        Invoice invoice = new Invoice();

        invoice.setOrder(order);

        invoice.setInvoiceNumber(
                "INV-" + System.currentTimeMillis() + "-"
                        + UUID.randomUUID().toString().substring(0, 5).toUpperCase());

        invoice.setInvoiceDate(LocalDate.now());

        invoice.setSubtotal(order.getSubtotal());

        invoice.setTaxAmount(order.getTaxAmount());

        invoice.setDiscountAmount(order.getDiscountAmount());

        invoice.setShippingCharge(order.getShippingCharge());

        invoice.setTotalAmount(order.getTotalAmount());

        invoice.setGstNumber(request.getGstNumber());

        invoice.setHsnCode(request.getHsnCode());

        invoice.setInvoiceStatus(InvoiceStatus.GENERATED);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return mapToResponse(savedInvoice);
    }
    // =====================================================
// GET ALL INVOICES
// =====================================================

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // =====================================================
// GET INVOICE BY ID
// =====================================================

    @Override
    public InvoiceResponseDTO getInvoiceById(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found."));

        return mapToResponse(invoice);
    }
    // =====================================================
// GET INVOICE BY INVOICE NUMBER
// =====================================================

    @Override
    public InvoiceResponseDTO getInvoiceByNumber(String invoiceNumber) {

        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found."));

        return mapToResponse(invoice);
    }
    // =====================================================
// GET INVOICE BY ORDER
// =====================================================

    @Override
    public InvoiceResponseDTO getInvoiceByOrder(Long orderId) {

        Invoice invoice = invoiceRepository.findByOrderOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found for this order."));

        return mapToResponse(invoice);
    }
    // =====================================================
// GET INVOICES BY STATUS
// =====================================================

    @Override
    public List<InvoiceResponseDTO> getInvoicesByStatus(
            InvoiceStatus invoiceStatus) {

        return invoiceRepository.findByInvoiceStatus(invoiceStatus)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // =====================================================
// UPDATE INVOICE STATUS
// =====================================================

    @Override
    public InvoiceResponseDTO updateInvoiceStatus(
            Long invoiceId,
            InvoiceStatus invoiceStatus) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found."));

        invoice.setInvoiceStatus(invoiceStatus);

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return mapToResponse(updatedInvoice);
    }
    // =====================================================
// DELETE INVOICE
// =====================================================

    @Override
    public void deleteInvoice(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found."));

        invoiceRepository.delete(invoice);
    }
    // =====================================================
// MAP ENTITY TO DTO
// =====================================================

    private InvoiceResponseDTO mapToResponse(Invoice invoice) {

        InvoiceResponseDTO response = new InvoiceResponseDTO();

        response.setInvoiceId(invoice.getInvoiceId());

        response.setOrderId(invoice.getOrder().getOrderId());

        response.setOrderNumber(invoice.getOrder().getOrderNumber());

        response.setInvoiceNumber(invoice.getInvoiceNumber());

        response.setInvoiceDate(invoice.getInvoiceDate());

        response.setSubtotal(invoice.getSubtotal());

        response.setTaxAmount(invoice.getTaxAmount());

        response.setDiscountAmount(invoice.getDiscountAmount());

        response.setShippingCharge(invoice.getShippingCharge());

        response.setTotalAmount(invoice.getTotalAmount());

        response.setGstNumber(invoice.getGstNumber());

        response.setHsnCode(invoice.getHsnCode());

        response.setInvoiceStatus(invoice.getInvoiceStatus());

        response.setCreatedAt(invoice.getCreatedAt());

        return response;
    }

    //invoice service by user
    @Override
    public List<InvoiceResponseDTO> getInvoicesByUser(Long userId) {

        return invoiceRepository.findByOrder_User_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}