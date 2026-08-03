package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.LoginRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginResponseDTO;
import com.pradnyasanskar.webstore.dto.RegisterRequestDTO;
import com.pradnyasanskar.webstore.dto.UserResponseDTO;
import com.pradnyasanskar.webstore.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO requestDTO) {

        UserResponseDTO response = authService.register(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {

        LoginResponseDTO response = authService.login(requestDTO);
        return ResponseEntity.ok(response);
    }
}