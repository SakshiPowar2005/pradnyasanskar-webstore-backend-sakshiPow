package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Get all notifications of a user (latest first)
    List<Notification> findByUserUserIdOrderByCreatedAtDesc(Long userId);

    // Get unread notifications of a user
    List<Notification> findByUserUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

    // Count unread notifications
    long countByUserUserIdAndIsReadFalse(Long userId);

}