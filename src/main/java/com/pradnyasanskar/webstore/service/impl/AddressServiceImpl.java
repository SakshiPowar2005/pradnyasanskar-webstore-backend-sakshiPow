package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.AddressRequestDTO;
import com.pradnyasanskar.webstore.dto.AddressResponseDTO;
import com.pradnyasanskar.webstore.entity.Address;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.AddressRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressResponseDTO addAddress(AddressRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found."));

        // If new address is default, remove previous default
        if (Boolean.TRUE.equals(request.getIsDefault())) {

            addressRepository.findByUser_UserIdAndIsDefaultTrue(user.getUserId())
                    .ifPresent(address -> {
                        address.setIsDefault(false);
                        addressRepository.save(address);
                    });
        }

        Address address = new Address();

        address.setUser(user);
        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setMobileNumber(request.getMobileNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault());

        return mapToDTO(addressRepository.save(address));
    }
    @Override
    public AddressResponseDTO updateAddress(Long addressId,
                                            AddressRequestDTO request) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address not found."));

        if (Boolean.TRUE.equals(request.getIsDefault())) {

            addressRepository.findByUser_UserIdAndIsDefaultTrue(
                            address.getUser().getUserId())
                    .ifPresent(defaultAddress -> {

                        if (!defaultAddress.getAddressId()
                                .equals(addressId)) {

                            defaultAddress.setIsDefault(false);
                            addressRepository.save(defaultAddress);
                        }
                    });
        }

        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setMobileNumber(request.getMobileNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault());

        return mapToDTO(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address not found."));

        addressRepository.delete(address);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address not found."));

        return mapToDTO(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAddressesByUser(Long userId) {

        return addressRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getDefaultAddress(Long userId) {

        Address address = addressRepository
                .findByUser_UserIdAndIsDefaultTrue(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Default address not found."));

        return mapToDTO(address);
    }

    @Override
    public AddressResponseDTO setDefaultAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address not found."));

        Long userId = address.getUser().getUserId();

        addressRepository.findByUser_UserIdAndIsDefaultTrue(userId)
                .ifPresent(defaultAddress -> {

                    defaultAddress.setIsDefault(false);
                    addressRepository.save(defaultAddress);
                });

        address.setIsDefault(true);

        return mapToDTO(addressRepository.save(address));
    }

    // =====================================================
    // Entity -> DTO
    // =====================================================

    private AddressResponseDTO mapToDTO(Address address) {

        AddressResponseDTO dto = new AddressResponseDTO();

        dto.setAddressId(address.getAddressId());
        dto.setUserId(address.getUser().getUserId());
        dto.setAddressType(address.getAddressType());
        dto.setFullName(address.getFullName());
        dto.setMobileNumber(address.getMobileNumber());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setLandmark(address.getLandmark());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setIsDefault(address.getIsDefault());
        dto.setCreatedAt(address.getCreatedAt());
        dto.setUpdatedAt(address.getUpdatedAt());

        return dto;
    }

}