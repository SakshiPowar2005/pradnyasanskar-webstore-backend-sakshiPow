package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.LoginRequestDTO;
import com.pradnyasanskar.webstore.dto.LoginResponseDTO;
import com.pradnyasanskar.webstore.dto.RegisterRequestDTO;
import com.pradnyasanskar.webstore.dto.UserResponseDTO;
import com.pradnyasanskar.webstore.dto.ForgotPasswordRequestDTO;
import com.pradnyasanskar.webstore.dto.ResetPasswordRequestDTO;
import com.pradnyasanskar.webstore.entity.PasswordResetToken;
import com.pradnyasanskar.webstore.repository.PasswordResetTokenRepository;
import com.pradnyasanskar.webstore.entity.Role;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.enums.AccountStatus;
import com.pradnyasanskar.webstore.exception.ResourceNotFoundException;
import com.pradnyasanskar.webstore.exception.UserAlreadyExistsException;
import com.pradnyasanskar.webstore.repository.RoleRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.security.JwtService;
import com.pradnyasanskar.webstore.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserResponseDTO register(RegisterRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        if (userRepository.existsByMobileNumber(requestDTO.getMobileNumber())) {
            throw new UserAlreadyExistsException("Mobile number already exists.");
        }

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("CUSTOMER role not found."));

        User user = new User();

        user.setRole(customerRole);
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setMobileNumber(requestDTO.getMobileNumber());

        user.setPasswordHash(passwordEncoder.encode(requestDTO.getPassword()));

        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setEmailVerified(false);
        user.setMobileVerified(false);
        user.setFailedLoginAttempts(0);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();

        response.setUserId(savedUser.getUserId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setMobileNumber(savedUser.getMobileNumber());
        response.setRole(savedUser.getRole().getRoleName());

        return response;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPasswordHash(),
                        List.of(
                                new SimpleGrantedAuthority(
                                        user.getRole().getRoleName()
                                )
                        )
                );

        String token = jwtService.generateToken(userDetails);

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobileNumber(user.getMobileNumber());
        userResponse.setRole(user.getRole().getRoleName());

        return new LoginResponseDTO(userResponse, token);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDTO requestDTO) {

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        // Generate a secure random token
        String token = java.util.UUID.randomUUID().toString();

        // Token expires after 15 minutes
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(expiresAt);

        passwordResetTokenRepository.save(resetToken);

        System.out.println("PASSWORD RESET TOKEN: " + token);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO requestDTO) {

        // 1. Find the reset token
        PasswordResetToken resetToken =
                passwordResetTokenRepository.findByToken(requestDTO.getToken())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Invalid reset token."));

        // 2. Check whether token has expired
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired.");
        }

        // 3. Get the user associated with the token
        User user = resetToken.getUser();

        // 4. Encrypt the new password
        String encodedPassword =
                passwordEncoder.encode(requestDTO.getNewPassword());

        // 5. Update user's password
        user.setPasswordHash(encodedPassword);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        // 6. Delete token so it cannot be reused
        passwordResetTokenRepository.delete(resetToken);
    }

}