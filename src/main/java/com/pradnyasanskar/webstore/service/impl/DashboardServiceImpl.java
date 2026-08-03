package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.*;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.exception.ResourceNotFoundException;
import com.pradnyasanskar.webstore.repository.AddressRepository;
import com.pradnyasanskar.webstore.repository.CartRepository;
import com.pradnyasanskar.webstore.repository.InvoiceRepository;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import com.pradnyasanskar.webstore.repository.PaymentRepository;
import com.pradnyasanskar.webstore.repository.RefundRepository;
import com.pradnyasanskar.webstore.repository.ShipmentRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.AddressService;
import com.pradnyasanskar.webstore.service.CartService;
import com.pradnyasanskar.webstore.service.DashboardService;
import com.pradnyasanskar.webstore.service.InvoiceService;
import com.pradnyasanskar.webstore.service.OrderService;
import com.pradnyasanskar.webstore.service.PaymentService;
import com.pradnyasanskar.webstore.service.RefundService;
import com.pradnyasanskar.webstore.service.ShipmentService;
import com.pradnyasanskar.webstore.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final ShipmentRepository shipmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;

    private final UserService userService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final RefundService refundService;
    private final ShipmentService shipmentService;
    private final InvoiceService invoiceService;
    private final CartService cartService;
    private final AddressService addressService;

    public DashboardServiceImpl(
            UserRepository userRepository,
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            RefundRepository refundRepository,
            ShipmentRepository shipmentRepository,
            InvoiceRepository invoiceRepository,
            CartRepository cartRepository,
            AddressRepository addressRepository,
            UserService userService,
            OrderService orderService,
            PaymentService paymentService,
            RefundService refundService,
            ShipmentService shipmentService,
            InvoiceService invoiceService,
            CartService cartService,
            AddressService addressService) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
        this.shipmentRepository = shipmentRepository;
        this.invoiceRepository = invoiceRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;

        this.userService = userService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.refundService = refundService;
        this.shipmentService = shipmentService;
        this.invoiceService = invoiceService;
        this.cartService = cartService;
        this.addressService = addressService;
    }
    // ============================================================
    // Complete Dashboard
    // ============================================================

    @Override
    public DashboardResponseDTO getDashboard(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        DashboardResponseDTO dashboard = new DashboardResponseDTO();

        // Customer Details
        dashboard.setUserId(user.getUserId());
        dashboard.setCustomerName(user.getFirstName() + " " + user.getLastName());
        dashboard.setEmail(user.getEmail());
        dashboard.setMobileNumber(user.getMobileNumber());

        // Orders
        List<OrderResponseDTO> orders = orderService.getOrdersByUser(userId);
        dashboard.setRecentOrders(orders);
        dashboard.setTotalOrders(orders.size());

        // Payments
        List<PaymentResponseDTO> payments = paymentService.getPaymentsByUser(userId);
        dashboard.setRecentPayments(payments);
        dashboard.setTotalPayments(payments.size());

        // Refunds
        List<RefundResponseDTO> refunds = refundService.getRefundsByUser(userId);
        dashboard.setRecentRefunds(refunds);
        dashboard.setTotalRefunds(refunds.size());

        // Shipments
        List<ShipmentResponseDTO> shipments = shipmentService.getShipmentsByUser(userId);
        dashboard.setRecentShipments(shipments);
        dashboard.setTotalShipments(shipments.size());

        // Invoices
        List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByUser(userId);
        dashboard.setRecentInvoices(invoices);
        dashboard.setTotalInvoices(invoices.size());

        // Cart
        CartResponseDTO cart = cartService.getCartByUser(userId);

        if (cart != null && cart.getItems() != null) {
            dashboard.setCartItems(cart.getItems().size());
        } else {
            dashboard.setCartItems(0);
        }

        return dashboard;
    }

    // ============================================================
    // Profile
    // ============================================================

    @Override
    public UserResponseDTO getProfile(Long userId) {
        return userService.getUserById(userId);
    }

    // ============================================================
    // Orders
    // ============================================================

    @Override
    public List<OrderResponseDTO> getOrders(Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    // ============================================================
    // Cart
    // ============================================================

    @Override
    public CartResponseDTO getCart(Long userId) {
        return cartService.getCartByUser(userId);
    }

    // ============================================================
    // Payments
    // ============================================================

    @Override
    public List<PaymentResponseDTO> getPayments(Long userId) {
        return paymentService.getPaymentsByUser(userId);
    }

    // ============================================================
    // Refunds
    // ============================================================

    @Override
    public List<RefundResponseDTO> getRefunds(Long userId) {
        return refundService.getRefundsByUser(userId);
    }

    // ============================================================
    // Shipments
    // ============================================================

    @Override
    public List<ShipmentResponseDTO> getShipments(Long userId) {
        return shipmentService.getShipmentsByUser(userId);
    }

    // ============================================================
    // Invoices
    // ============================================================

    @Override
    public List<InvoiceResponseDTO> getInvoices(Long userId) {
        return invoiceService.getInvoicesByUser(userId);
    }

    // ============================================================
    // Addresses
    // ============================================================

    @Override
    public List<AddressResponseDTO> getAddresses(Long userId) {
        return addressService.getAddressesByUser(userId);
    }
}