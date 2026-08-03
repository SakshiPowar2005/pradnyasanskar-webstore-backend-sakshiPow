package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.NotificationRequestDTO;
import com.pradnyasanskar.webstore.dto.NotificationResponseDTO;
import com.pradnyasanskar.webstore.entity.Notification;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.NotificationRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // CREATE NOTIFICATION
    // ============================================================

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setNotificationType(request.getNotificationType());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setActionUrl(request.getActionUrl());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification savedNotification =
                notificationRepository.save(notification);

        return mapToResponse(savedNotification);
    }

    // ============================================================
    // GET NOTIFICATION BY ID
    // ============================================================

    @Override
    public NotificationResponseDTO getNotificationById(Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found."));

        return mapToResponse(notification);
    }

    // ============================================================
    // GET ALL NOTIFICATIONS
    // ============================================================

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // GET NOTIFICATIONS BY USER
    // ============================================================

    @Override
    public List<NotificationResponseDTO> getNotificationsByUser(Long userId) {

        return notificationRepository
                .findByUserUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // GET UNREAD NOTIFICATIONS
    // ============================================================

    @Override
    public List<NotificationResponseDTO> getUnreadNotifications(Long userId) {

        return notificationRepository
                .findByUserUserIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // GET UNREAD NOTIFICATION COUNT
    // ============================================================

    @Override
    public Long getUnreadCount(Long userId) {

        return notificationRepository
                .countByUserUserIdAndIsReadFalse(userId);
    }

    // ============================================================
    // MARK NOTIFICATION AS READ
    // ============================================================

    @Override
    public NotificationResponseDTO markAsRead(Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found."));

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());

        Notification updatedNotification =
                notificationRepository.save(notification);

        return mapToResponse(updatedNotification);
    }

    // ============================================================
    // DELETE NOTIFICATION
    // ============================================================

    @Override
    public void deleteNotification(Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found."));

        notificationRepository.delete(notification);
    }

    // ============================================================
    // MAP ENTITY TO RESPONSE DTO
    // ============================================================

    private NotificationResponseDTO mapToResponse(Notification notification) {

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        response.setNotificationId(notification.getNotificationId());

        response.setUserId(notification.getUser().getUserId());

        response.setUserName(notification.getUser().getFirstName()
                + " "
                + notification.getUser().getLastName());

        response.setNotificationType(notification.getNotificationType());

        response.setTitle(notification.getTitle());

        response.setMessage(notification.getMessage());

        response.setIsRead(notification.getIsRead());

        response.setActionUrl(notification.getActionUrl());

        response.setCreatedAt(notification.getCreatedAt());

        response.setReadAt(notification.getReadAt());

        return response;
    }

}