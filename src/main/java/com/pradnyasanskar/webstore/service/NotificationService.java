package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.NotificationRequestDTO;
import com.pradnyasanskar.webstore.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(
            NotificationRequestDTO request);

    NotificationResponseDTO getNotificationById(
            Long notificationId);

    List<NotificationResponseDTO> getAllNotifications();

    List<NotificationResponseDTO> getNotificationsByUser(
            Long userId);

    List<NotificationResponseDTO> getUnreadNotifications(
            Long userId);

    Long getUnreadCount(
            Long userId);

    NotificationResponseDTO markAsRead(
            Long notificationId);

    void deleteNotification(
            Long notificationId);
}