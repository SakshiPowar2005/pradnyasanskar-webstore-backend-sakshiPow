package com.pradnyasanskar.webstore.dto;

import java.util.List;

public class DashboardResponseDTO {

    // Customer
    private Long userId;
    private String customerName;
    private String email;
    private String mobileNumber;

    // Counts
    private Integer totalOrders;
    private Integer totalPayments;
    private Integer totalRefunds;
    private Integer totalInvoices;
    private Integer totalShipments;
    private Integer cartItems;

    // Data
    private List<OrderResponseDTO> recentOrders;
    private List<PaymentResponseDTO> recentPayments;
    private List<RefundResponseDTO> recentRefunds;
    private List<ShipmentResponseDTO> recentShipments;
    private List<InvoiceResponseDTO> recentInvoices;

    public DashboardResponseDTO() {
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Integer totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Integer getTotalRefunds() {
        return totalRefunds;
    }

    public void setTotalRefunds(Integer totalRefunds) {
        this.totalRefunds = totalRefunds;
    }

    public Integer getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Integer totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public Integer getTotalShipments() {
        return totalShipments;
    }

    public void setTotalShipments(Integer totalShipments) {
        this.totalShipments = totalShipments;
    }

    public Integer getCartItems() {
        return cartItems;
    }

    public void setCartItems(Integer cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderResponseDTO> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<OrderResponseDTO> recentOrders) {
        this.recentOrders = recentOrders;
    }

    public List<PaymentResponseDTO> getRecentPayments() {
        return recentPayments;
    }

    public void setRecentPayments(List<PaymentResponseDTO> recentPayments) {
        this.recentPayments = recentPayments;
    }

    public List<RefundResponseDTO> getRecentRefunds() {
        return recentRefunds;
    }

    public void setRecentRefunds(List<RefundResponseDTO> recentRefunds) {
        this.recentRefunds = recentRefunds;
    }

    public List<ShipmentResponseDTO> getRecentShipments() {
        return recentShipments;
    }

    public void setRecentShipments(List<ShipmentResponseDTO> recentShipments) {
        this.recentShipments = recentShipments;
    }

    public List<InvoiceResponseDTO> getRecentInvoices() {
        return recentInvoices;
    }

    public void setRecentInvoices(List<InvoiceResponseDTO> recentInvoices) {
        this.recentInvoices = recentInvoices;
    }
}