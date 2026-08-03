package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pradnyasanskar.webstore.enums.AccountStatus;
import java.util.List;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<User> findByEmail(String email);


    long countByAccountStatus(AccountStatus accountStatus);

    List<User> findByAccountStatus(AccountStatus accountStatus);
}
