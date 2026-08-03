package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.UpdateUserRequestDTO;
import com.pradnyasanskar.webstore.dto.UserResponseDTO;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.pradnyasanskar.webstore.enums.AccountStatus;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (user.getAccountStatus() == AccountStatus.INACTIVE) {

            throw new RuntimeException("User has been deleted.");
        }
        return convertToResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository
                .findByAccountStatus(AccountStatus.ACTIVE)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        // Prevent deleting Admin
        if (user.getRole() != null &&
                "ADMIN".equalsIgnoreCase(user.getRole().getRoleName())) {

            throw new RuntimeException("Admin user cannot be deleted.");
        }

        // Already inactive
        if (user.getAccountStatus() == AccountStatus.INACTIVE) {

            throw new RuntimeException("User already deleted.");
        }

        // Soft Delete
        user.setAccountStatus(AccountStatus.INACTIVE);

        userRepository.save(user);
    }

    @Override
    public UserResponseDTO updateUser(Long userId,
                                      UpdateUserRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());

        User updatedUser = userRepository.save(user);

        return convertToResponseDTO(updatedUser);
    }

    private UserResponseDTO convertToResponseDTO(User user) {

        return new UserResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole() != null
                        ? user.getRole().getRoleName()
                        : null
        );
    }
}