package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.UpdateUserRequestDTO;
import com.pradnyasanskar.webstore.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO getUserById(Long userId);

    List<UserResponseDTO> getAllUsers();

    void deleteUser(Long userId);

    UserResponseDTO updateUser(Long userId,
                               UpdateUserRequestDTO request);
}