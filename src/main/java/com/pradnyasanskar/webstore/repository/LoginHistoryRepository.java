package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.LoginHistory;
import com.pradnyasanskar.webstore.entity.LoginStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    List<LoginHistory> findByUser_UserId(Long userId);

    List<LoginHistory> findByLoginStatus(LoginStatus loginStatus);

    List<LoginHistory> findByUser_UserIdAndLoginStatus(
            Long userId,
            LoginStatus loginStatus
    );
}