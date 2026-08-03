package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.LoginRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginResponseDTO;
import com.pradnyasanskar.webstore.dto.RegisterRequestDTO;
import com.pradnyasanskar.webstore.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(RegisterRequestDTO requestDTO);

    LoginResponseDTO login(LoginRequestDTO requestDTO);

}