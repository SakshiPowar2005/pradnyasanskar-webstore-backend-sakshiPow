package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.AddressRequestDTO;
import com.pradnyasanskar.webstore.dto.AddressResponseDTO;
import com.pradnyasanskar.webstore.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // ==========================================================
    // Add Address
    // ==========================================================

    @PostMapping
    public ResponseEntity<AddressResponseDTO> addAddress(
            @RequestBody AddressRequestDTO request) {

        return new ResponseEntity<>(
                addressService.addAddress(request),
                HttpStatus.CREATED
        );
    }

    // ==========================================================
    // Update Address
    // ==========================================================

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequestDTO request) {

        return ResponseEntity.ok(
                addressService.updateAddress(addressId, request)
        );
    }

    // ==========================================================
    // Delete Address
    // ==========================================================

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long addressId) {

        addressService.deleteAddress(addressId);

        return ResponseEntity.ok("Address deleted successfully.");
    }

    // ==========================================================
    // Get Address By Id
    // ==========================================================

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddressById(
            @PathVariable Long addressId) {

        return ResponseEntity.ok(
                addressService.getAddressById(addressId)
        );
    }

    // ==========================================================
    // Get All Addresses Of User
    // ==========================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAddressesByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                addressService.getAddressesByUser(userId)
        );
    }

    // ==========================================================
    // Get Default Address
    // ==========================================================

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<AddressResponseDTO> getDefaultAddress(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                addressService.getDefaultAddress(userId)
        );
    }

    // ==========================================================
    // Set Default Address
    // ==========================================================

    @PutMapping("/{addressId}/default")
    public ResponseEntity<AddressResponseDTO> setDefaultAddress(
            @PathVariable Long addressId) {

        return ResponseEntity.ok(
                addressService.setDefaultAddress(addressId)
        );
    }
}