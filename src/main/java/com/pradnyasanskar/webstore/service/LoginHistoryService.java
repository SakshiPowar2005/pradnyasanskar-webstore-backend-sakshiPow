package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.LoginHistoryRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginHistoryResponseDTO;
import com.pradnyasanskar.webstore.entity.LoginStatus;

import java.util.List;

public interface LoginHistoryService {

    LoginHistoryResponseDTO createLoginHistory(
            LoginHistoryRequestDTO request);

    LoginHistoryResponseDTO getLoginHistoryById(
            Long loginHistoryId);

    List<LoginHistoryResponseDTO> getAllLoginHistory();

    List<LoginHistoryResponseDTO> getLoginHistoryByUser(
            Long userId);

    List<LoginHistoryResponseDTO> getLoginHistoryByStatus(
            LoginStatus loginStatus);

    List<LoginHistoryResponseDTO> getLoginHistoryByUserAndStatus(
            Long userId,
            LoginStatus loginStatus);

    void deleteLoginHistory(
            Long loginHistoryId);
}