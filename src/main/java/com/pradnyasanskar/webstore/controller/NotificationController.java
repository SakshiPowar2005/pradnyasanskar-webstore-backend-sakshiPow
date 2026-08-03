package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.NotificationRequestDTO;
import com.pradnyasanskar.webstore.dto.NotificationResponseDTO;
import com.pradnyasanskar.webstore.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // ============================================================
    // CREATE NOTIFICATION
    // ============================================================

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(
            @RequestBody NotificationRequestDTO request) {

        NotificationResponseDTO response =
                notificationService.createNotification(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ============================================================
    // GET NOTIFICATION BY ID
    // ============================================================

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(
            @PathVariable Long notificationId) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(notificationId));
    }

    // ============================================================
    // GET ALL NOTIFICATIONS
    // ============================================================

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications());
    }

    // ============================================================
    // GET NOTIFICATIONS OF A USER
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByUser(userId));
    }

    // ============================================================
    // GET UNREAD NOTIFICATIONS
    // ============================================================

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnreadNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadNotifications(userId));
    }

    // ============================================================
    // GET UNREAD COUNT
    // ============================================================

    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<Long> getUnreadCount(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadCount(userId));
    }

    // ============================================================
    // MARK NOTIFICATION AS READ
    // ============================================================

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(
            @PathVariable Long notificationId) {

        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId));
    }

    // ============================================================
    // DELETE NOTIFICATION
    // ============================================================

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long notificationId) {

        notificationService.deleteNotification(notificationId);

        return ResponseEntity.ok("Notification deleted successfully.");
    }

}