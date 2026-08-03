package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.*;
import com.pradnyasanskar.webstore.entity.*;
import com.pradnyasanskar.webstore.repository.*;
import com.pradnyasanskar.webstore.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final InventoryBatchRepository inventoryBatchRepository;
    private final StockLedgerRepository stockLedgerRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            ProductVariantRepository productVariantRepository,
            InventoryBatchRepository inventoryBatchRepository,
            StockLedgerRepository stockLedgerRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.inventoryBatchRepository = inventoryBatchRepository;
        this.stockLedgerRepository = stockLedgerRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();

        order.setUser(user);

        order.setOrderNumber(
                "ORD-" +
                        System.currentTimeMillis() +
                        "-"
                        + UUID.randomUUID().toString().substring(0,5)
        );

        // Address Snapshot

        order.setFullName(request.getFullName());
        order.setMobileNumber(request.getMobileNumber());
        order.setAddressLine1(request.getAddressLine1());
        order.setAddressLine2(request.getAddressLine2());
        order.setLandmark(request.getLandmark());
        order.setCity(request.getCity());
        order.setState(request.getState());
        order.setPostalCode(request.getPostalCode());

        if(request.getCountry()==null)
            order.setCountry("India");
        else
            order.setCountry(request.getCountry());

        order.setNotes(request.getNotes());

        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);

        BigDecimal subtotal = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        // ===============================
        // Process Every Item
        // ===============================

        for(OrderItemRequestDTO itemRequest : request.getItems()) {

            ProductVariant variant =
                    productVariantRepository.findById(itemRequest.getVariantId())
                            .orElseThrow(() ->
                                    new RuntimeException("Variant not found"));

            int requiredQty = itemRequest.getQuantity();

            List<InventoryBatch> batches =
                    inventoryBatchRepository
                            .findByProductVariantVariantIdOrderByExpiryDateAsc(
                                    variant.getVariantId());

            int available = batches.stream()
                    .mapToInt(InventoryBatch::getQuantityInStock)
                    .sum();

            if(available < requiredQty)
                throw new RuntimeException(
                        "Insufficient stock for SKU : "
                                + variant.getSku());

            BigDecimal itemSubtotal =
                    variant.getSellingPrice()
                            .multiply(BigDecimal.valueOf(requiredQty));

            subtotal = subtotal.add(itemSubtotal);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProductVariant(variant);

            orderItem.setProductName(
                    variant.getProduct().getProductName());

            orderItem.setSku(
                    variant.getSku());

            orderItem.setQuantity(requiredQty);

            orderItem.setUnitPrice(
                    variant.getSellingPrice());

            orderItem.setDiscountAmount(BigDecimal.ZERO);

            orderItem.setTaxAmount(BigDecimal.ZERO);

            orderItem.setSubtotal(itemSubtotal);

            orderItems.add(orderItem);

            // ==========================================
            // FEFO STOCK DEDUCTION
            // ==========================================

            int remaining = requiredQty;

            for(InventoryBatch batch : batches){

                if(remaining==0)
                    break;

                int stock = batch.getQuantityInStock();

                if(stock<=0)
                    continue;

                int deduct = Math.min(stock, remaining);

                batch.setQuantityInStock(stock-deduct);

                inventoryBatchRepository.save(batch);

                remaining -= deduct;

                // ==========================
                // STOCK LEDGER ENTRY
                // ==========================

                StockLedger ledger = new StockLedger();

                ledger.setInventoryBatch(batch);

                ledger.setTransactionType(
                        StockTransactionType.SALE);

                ledger.setQuantity(-deduct);

                ledger.setBalanceAfterTransaction(
                        batch.getQuantityInStock());

                ledger.setReferenceType("ORDER");

                ledger.setRemarks(
                        "Order : " + order.getOrderNumber());

                ledger.setCreatedBy(user);

                stockLedgerRepository.save(ledger);

            }

        }

        order.setSubtotal(subtotal);

        order.setDiscountAmount(BigDecimal.ZERO);

        order.setTaxAmount(BigDecimal.ZERO);

        order.setShippingCharge(BigDecimal.ZERO);

        order.setTotalAmount(subtotal);

        Order savedOrder =
                orderRepository.save(order);

        for(OrderItem item : orderItems){

            item.setOrder(savedOrder);

            orderItemRepository.save(item);
        }

        return mapToResponse(savedOrder);

    }
    // =========================================================
    // Get All Orders
    // =========================================================

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // Get Order By ID
    // =========================================================

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        return mapToResponse(order);
    }

    // =========================================================
    // Get Order By Order Number
    // =========================================================

    @Override
    public OrderResponseDTO getOrderByOrderNumber(String orderNumber) {

        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        return mapToResponse(order);
    }

    // =========================================================
    // Get Orders By User
    // =========================================================

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {

        return orderRepository.findByUserUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // Get Orders By Status
    // =========================================================

    @Override
    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {

        return orderRepository.findByOrderStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // Get Orders By Payment Status
    // =========================================================

    @Override
    public List<OrderResponseDTO> getOrdersByPaymentStatus(PaymentStatus status) {

        return orderRepository.findByPaymentStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    // =========================================================
    // Update Order Status
    // =========================================================

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId,
                                              OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        order.setOrderStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return mapToResponse(updatedOrder);
    }

    // =========================================================
    // Update Payment Status
    // =========================================================

    @Override
    public OrderResponseDTO updatePaymentStatus(Long orderId,
                                                PaymentStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        order.setPaymentStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return mapToResponse(updatedOrder);
    }

    // =========================================================
    // Cancel Order
    // =========================================================

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled.");
        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled.");
        }

        List<OrderItem> items =
                orderItemRepository.findByOrderOrderId(orderId);

        for (OrderItem item : items) {

            List<InventoryBatch> batches =
                    inventoryBatchRepository
                            .findByProductVariantVariantIdOrderByExpiryDateAsc(
                                    item.getProductVariant().getVariantId());

            int remaining = item.getQuantity();

            for (InventoryBatch batch : batches) {

                if (remaining == 0)
                    break;

                int restore = remaining;

                batch.setQuantityInStock(
                        batch.getQuantityInStock() + restore);

                inventoryBatchRepository.save(batch);

                StockLedger ledger = new StockLedger();

                ledger.setInventoryBatch(batch);
                ledger.setTransactionType(
                        StockTransactionType.CANCELLED);

                ledger.setQuantity(restore);

                ledger.setBalanceAfterTransaction(
                        batch.getQuantityInStock());

                ledger.setReferenceType("ORDER_CANCEL");

                ledger.setReferenceId(order.getOrderId());

                ledger.setRemarks(
                        "Cancelled Order : "
                                + order.getOrderNumber());

                ledger.setCreatedBy(order.getUser());

                stockLedgerRepository.save(ledger);

                remaining -= restore;
            }
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    // =========================================================
    // Mapping Methods
    // =========================================================

    private OrderResponseDTO mapToResponse(Order order) {

        OrderResponseDTO response =
                new OrderResponseDTO();

        response.setOrderId(order.getOrderId());
        response.setOrderNumber(order.getOrderNumber());

        response.setUserId(order.getUser().getUserId());

        response.setCustomerName(
                order.getUser().getFirstName() + " "
                        + order.getUser().getLastName());

        response.setFullName(order.getFullName());
        response.setMobileNumber(order.getMobileNumber());

        response.setAddressLine1(order.getAddressLine1());
        response.setAddressLine2(order.getAddressLine2());

        response.setLandmark(order.getLandmark());

        response.setCity(order.getCity());
        response.setState(order.getState());

        response.setPostalCode(order.getPostalCode());

        response.setCountry(order.getCountry());

        response.setOrderStatus(order.getOrderStatus());

        response.setPaymentStatus(order.getPaymentStatus());

        response.setSubtotal(order.getSubtotal());

        response.setDiscountAmount(order.getDiscountAmount());

        response.setTaxAmount(order.getTaxAmount());

        response.setShippingCharge(order.getShippingCharge());

        response.setTotalAmount(order.getTotalAmount());

        response.setNotes(order.getNotes());

        response.setOrderedAt(order.getOrderedAt());

        List<OrderItemResponseDTO> itemResponses =
                orderItemRepository.findByOrderOrderId(
                                order.getOrderId())
                        .stream()
                        .map(this::mapOrderItem)
                        .toList();

        response.setItems(itemResponses);

        return response;
    }

    private OrderItemResponseDTO mapOrderItem(
            OrderItem item) {

        OrderItemResponseDTO dto =
                new OrderItemResponseDTO();

        dto.setOrderItemId(item.getOrderItemId());

        dto.setVariantId(
                item.getProductVariant().getVariantId());

        dto.setProductName(item.getProductName());

        dto.setVariantName(
                item.getProductVariant().getVariantName());

        dto.setQuantity(item.getQuantity());

        dto.setUnitPrice(item.getUnitPrice());

        dto.setDiscountAmount(item.getDiscountAmount());

        dto.setTaxAmount(item.getTaxAmount());

        dto.setSubtotal(item.getSubtotal());

        return dto;
    }

}