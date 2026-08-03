package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Address;
import com.pradnyasanskar.webstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // Get all addresses of a user
    List<Address> findByUser(User user);

    // Get all addresses by userId
    List<Address> findByUser_UserId(Long userId);

    // Get default address of a user
    Optional<Address> findByUser_UserIdAndIsDefaultTrue(Long userId);

    // Check if address belongs to user
    Optional<Address> findByAddressIdAndUser_UserId(Long addressId, Long userId);

    // Check whether default address exists
    boolean existsByUser_UserIdAndIsDefaultTrue(Long userId);

}