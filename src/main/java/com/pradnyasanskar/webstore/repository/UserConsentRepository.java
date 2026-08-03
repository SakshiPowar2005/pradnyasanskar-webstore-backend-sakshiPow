package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.ConsentType;
import com.pradnyasanskar.webstore.entity.UserConsent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserConsentRepository extends JpaRepository<UserConsent, Long> {

    List<UserConsent> findByUser_UserId(Long userId);

    List<UserConsent> findByConsentType(ConsentType consentType);

    List<UserConsent> findByUser_UserIdAndConsentType(
            Long userId,
            ConsentType consentType
    );

}