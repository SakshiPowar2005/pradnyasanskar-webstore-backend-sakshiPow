package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.LoginHistoryRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginHistoryResponseDTO;
import com.pradnyasanskar.webstore.entity.LoginHistory;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.LoginHistoryRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.LoginHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final UserRepository userRepository;

    public LoginHistoryServiceImpl(
            LoginHistoryRepository loginHistoryRepository,
            UserRepository userRepository) {

        this.loginHistoryRepository = loginHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LoginHistoryResponseDTO createLoginHistory(
            LoginHistoryRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        LoginHistory loginHistory = new LoginHistory();

        loginHistory.setUser(user);
        loginHistory.setLoginStatus(request.getLoginStatus());
        loginHistory.setIpAddress(request.getIpAddress());
        loginHistory.setDeviceName(request.getDeviceName());
        loginHistory.setBrowser(request.getBrowser());
        loginHistory.setOperatingSystem(request.getOperatingSystem());

        loginHistory.setLoginTime(
                request.getLoginTime() != null
                        ? request.getLoginTime()
                        : LocalDateTime.now());

        loginHistory.setLogoutTime(request.getLogoutTime());
        loginHistory.setFailureReason(request.getFailureReason());
        loginHistory.setSessionId(request.getSessionId());

        LoginHistory saved = loginHistoryRepository.save(loginHistory);

        return mapToResponse(saved);
    }
    // ============================================================
    // GET LOGIN HISTORY BY ID
    // ============================================================

    @Override
    public LoginHistoryResponseDTO getLoginHistoryById(Long loginHistoryId) {

        LoginHistory loginHistory = loginHistoryRepository.findById(loginHistoryId)
                .orElseThrow(() ->
                        new RuntimeException("Login history not found"));

        return mapToResponse(loginHistory);
    }

    // ============================================================
    // GET ALL LOGIN HISTORY
    // ============================================================

    @Override
    public List<LoginHistoryResponseDTO> getAllLoginHistory() {

        return loginHistoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET LOGIN HISTORY BY USER
    // ============================================================

    @Override
    public List<LoginHistoryResponseDTO> getLoginHistoryByUser(Long userId) {

        return loginHistoryRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET LOGIN HISTORY BY STATUS
    // ============================================================

    @Override
    public List<LoginHistoryResponseDTO> getLoginHistoryByStatus(
            com.pradnyasanskar.webstore.entity.LoginStatus loginStatus) {

        return loginHistoryRepository.findByLoginStatus(loginStatus)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET LOGIN HISTORY BY USER & STATUS
    // ============================================================

    @Override
    public List<LoginHistoryResponseDTO> getLoginHistoryByUserAndStatus(
            Long userId,
            com.pradnyasanskar.webstore.entity.LoginStatus loginStatus) {

        return loginHistoryRepository
                .findByUser_UserIdAndLoginStatus(userId, loginStatus)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // DELETE LOGIN HISTORY
    // ============================================================

    @Override
    public void deleteLoginHistory(Long loginHistoryId) {

        LoginHistory loginHistory = loginHistoryRepository.findById(loginHistoryId)
                .orElseThrow(() ->
                        new RuntimeException("Login history not found"));

        loginHistoryRepository.delete(loginHistory);
    }

    // ============================================================
    // ENTITY TO DTO
    // ============================================================

    private LoginHistoryResponseDTO mapToResponse(LoginHistory loginHistory) {

        LoginHistoryResponseDTO response = new LoginHistoryResponseDTO();

        response.setLoginHistoryId(loginHistory.getLoginHistoryId());

        response.setUserId(loginHistory.getUser().getUserId());

        response.setLoginStatus(loginHistory.getLoginStatus());

        response.setIpAddress(loginHistory.getIpAddress());

        response.setDeviceName(loginHistory.getDeviceName());

        response.setBrowser(loginHistory.getBrowser());

        response.setOperatingSystem(loginHistory.getOperatingSystem());

        response.setLoginTime(loginHistory.getLoginTime());

        response.setLogoutTime(loginHistory.getLogoutTime());

        response.setFailureReason(loginHistory.getFailureReason());

        response.setSessionId(loginHistory.getSessionId());

        return response;
    }
}
