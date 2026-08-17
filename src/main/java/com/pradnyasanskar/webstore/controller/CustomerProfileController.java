package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.CustomerProfileRequestDTO;
import com.pradnyasanskar.webstore.dto.CustomerProfileResponseDTO;
import com.pradnyasanskar.webstore.service.CustomerProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerProfileController {

    @Autowired
    private CustomerProfileService customerProfileService;

    @GetMapping("/profile")
    public CustomerProfileResponseDTO getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return customerProfileService.getProfile(email);
    }

    @PutMapping("/profile")
    public CustomerProfileResponseDTO updateProfile(
            Authentication authentication,
            @RequestBody CustomerProfileRequestDTO requestDTO) {

        String email = authentication.getName();

        return customerProfileService.updateProfile(
                email,
                requestDTO
        );
    }
}