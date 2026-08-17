package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.CustomerProfileRequestDTO;
import com.pradnyasanskar.webstore.dto.CustomerProfileResponseDTO;

public interface CustomerProfileService {

    CustomerProfileResponseDTO getProfile(String email);

    CustomerProfileResponseDTO updateProfile(
            String email,
            CustomerProfileRequestDTO requestDTO
    );
}