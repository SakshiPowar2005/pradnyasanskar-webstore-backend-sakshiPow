package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.AddressRequestDTO;
import com.pradnyasanskar.webstore.dto.AddressResponseDTO;

import java.util.List;

public interface AddressService {

    // Add Address
    AddressResponseDTO addAddress(AddressRequestDTO request);

    // Update Address
    AddressResponseDTO updateAddress(Long addressId, AddressRequestDTO request);

    // Delete Address
    void deleteAddress(Long addressId);

    // Get Address By Id
    AddressResponseDTO getAddressById(Long addressId);

    // Get All Addresses Of User
    List<AddressResponseDTO> getAddressesByUser(Long userId);

    // Get Default Address
    AddressResponseDTO getDefaultAddress(Long userId);

    // Set Default Address
    AddressResponseDTO setDefaultAddress(Long addressId);

}