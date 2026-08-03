package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.LoginHistoryRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginHistoryResponseDTO;
import com.pradnyasanskar.webstore.entity.LoginStatus;
import com.pradnyasanskar.webstore.service.LoginHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/login-history")
public class LoginHistoryController {

    private final LoginHistoryService loginHistoryService;

    public LoginHistoryController(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    // ============================================================
    // CREATE LOGIN HISTORY
    // ============================================================

    @PostMapping
    public ResponseEntity<LoginHistoryResponseDTO> createLoginHistory(
            @RequestBody LoginHistoryRequestDTO request) {

        return new ResponseEntity<>(
                loginHistoryService.createLoginHistory(request),
                HttpStatus.CREATED
        );
    }

    // ============================================================
    // GET LOGIN HISTORY BY ID
    // ============================================================

    @GetMapping("/{loginHistoryId}")
    public ResponseEntity<LoginHistoryResponseDTO> getLoginHistoryById(
            @PathVariable Long loginHistoryId) {

        return ResponseEntity.ok(
                loginHistoryService.getLoginHistoryById(loginHistoryId)
        );
    }

    // ============================================================
    // GET ALL LOGIN HISTORY
    // ============================================================

    @GetMapping
    public ResponseEntity<List<LoginHistoryResponseDTO>> getAllLoginHistory() {

        return ResponseEntity.ok(
                loginHistoryService.getAllLoginHistory()
        );
    }

    // ============================================================
    // GET LOGIN HISTORY BY USER
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoginHistoryResponseDTO>> getLoginHistoryByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                loginHistoryService.getLoginHistoryByUser(userId)
        );
    }

    // ============================================================
    // GET LOGIN HISTORY BY STATUS
    // ============================================================

    @GetMapping("/status/{loginStatus}")
    public ResponseEntity<List<LoginHistoryResponseDTO>> getLoginHistoryByStatus(
            @PathVariable LoginStatus loginStatus) {

        return ResponseEntity.ok(
                loginHistoryService.getLoginHistoryByStatus(loginStatus)
        );
    }

    // ============================================================
    // GET LOGIN HISTORY BY USER & STATUS
    // ============================================================

    @GetMapping("/user/{userId}/status/{loginStatus}")
    public ResponseEntity<List<LoginHistoryResponseDTO>> getLoginHistoryByUserAndStatus(
            @PathVariable Long userId,
            @PathVariable LoginStatus loginStatus) {

        return ResponseEntity.ok(
                loginHistoryService.getLoginHistoryByUserAndStatus(
                        userId,
                        loginStatus
                )
        );
    }

    // ============================================================
    // DELETE LOGIN HISTORY
    // ============================================================

    @DeleteMapping("/{loginHistoryId}")
    public ResponseEntity<String> deleteLoginHistory(
            @PathVariable Long loginHistoryId) {

        loginHistoryService.deleteLoginHistory(loginHistoryId);

        return ResponseEntity.ok("Login history deleted successfully.");
    }

}