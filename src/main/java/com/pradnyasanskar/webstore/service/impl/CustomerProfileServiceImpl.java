package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.CustomerProfileRequestDTO;
import com.pradnyasanskar.webstore.dto.CustomerProfileResponseDTO;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.exception.ResourceNotFoundException;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.CustomerProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {

    @Autowired
    private UserRepository userRepository;

    // ==========================
    // GET CUSTOMER PROFILE
    // ==========================
    @Override
    public CustomerProfileResponseDTO getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        CustomerProfileResponseDTO response =
                new CustomerProfileResponseDTO();

        response.setUserId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setMobileNumber(user.getMobileNumber());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setGender(user.getGender());
        response.setProfileImageUrl(user.getProfileImageUrl());
        response.setRole(user.getRole().getRoleName());

        return response;
    }

    // ==========================
    // UPDATE CUSTOMER PROFILE
    // ==========================
    @Override
    public CustomerProfileResponseDTO updateProfile(
            String email,
            CustomerProfileRequestDTO requestDTO) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        // Update fields
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setMobileNumber(requestDTO.getMobileNumber());
        user.setDateOfBirth(requestDTO.getDateOfBirth());
        user.setGender(requestDTO.getGender());
        user.setProfileImageUrl(requestDTO.getProfileImageUrl());

        // Save updated user
        User updatedUser = userRepository.save(user);

        // Create response
        CustomerProfileResponseDTO response =
                new CustomerProfileResponseDTO();

        response.setUserId(updatedUser.getUserId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());
        response.setMobileNumber(updatedUser.getMobileNumber());
        response.setDateOfBirth(updatedUser.getDateOfBirth());
        response.setGender(updatedUser.getGender());
        response.setProfileImageUrl(updatedUser.getProfileImageUrl());
        response.setRole(updatedUser.getRole().getRoleName());

        return response;
    }
}