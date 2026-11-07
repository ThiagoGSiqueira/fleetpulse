package com.fleetpulse.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.User;
import com.fleetpulse.domain.UserRole;
import com.fleetpulse.exception.BusinessRuleException;
import com.fleetpulse.exception.ConflictException;
import com.fleetpulse.exception.InvalidCredentialsException;
import com.fleetpulse.mapper.UserMapper;
import com.fleetpulse.repository.UserRepository;
import com.fleetpulse.security.TokenService;
import com.fleetpulse.web.dto.UserLoginRequestDTO;
import com.fleetpulse.web.dto.UserRegisterRequestDTO;
import com.fleetpulse.web.dto.UserResponseLoginDTO;
import com.fleetpulse.web.dto.UserResponseRegisterDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseRegisterDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {
        if (userRegisterRequestDTO.role() == UserRole.ROLE_DRIVER) {
            throw new BusinessRuleException("Use /drivers to create drivers.");
        }

        if (userRepository.existsByEmail(userRegisterRequestDTO.email())) {
            throw new ConflictException("Email already in use.");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequestDTO.password());
        User user = userMapper.toEntity(userRegisterRequestDTO, encodedPassword);
        userRepository.save(user);
        return userMapper.toRegisterDto(user);
    }

    @Transactional
    public UserResponseLoginDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        User user = userRepository.findByEmail(userLoginRequestDTO.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        String token = tokenService.generateToken(user);

        return userMapper.toLoginDto(user, token);
    }
}
