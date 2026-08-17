package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.LoginRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginResponseDTO;
import com.pradnyasanskar.webstore.dto.RegisterRequestDTO;
import com.pradnyasanskar.webstore.dto.UserResponseDTO;
import com.pradnyasanskar.webstore.dto.ForgotPasswordRequestDTO;
import com.pradnyasanskar.webstore.dto.ResetPasswordRequestDTO;

public interface AuthService {

    UserResponseDTO register(RegisterRequestDTO requestDTO);

    LoginResponseDTO login(LoginRequestDTO requestDTO);

    void forgotPassword(ForgotPasswordRequestDTO requestDTO);

    void resetPassword(ResetPasswordRequestDTO requestDTO);
}