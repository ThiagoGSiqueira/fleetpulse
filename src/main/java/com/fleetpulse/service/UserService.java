package com.fleetpulse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.User;
import com.fleetpulse.exception.InvalidCredentialsException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.UserMapper;
import com.fleetpulse.repository.UserRepository;
import com.fleetpulse.web.dto.UserLoginRequestDTO;
import com.fleetpulse.web.dto.UserRegisterRequestDTO;
import com.fleetpulse.web.dto.UserResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {
        User user = userMapper.toEntity(userRegisterRequestDTO);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        User user = userRepository.findByEmail(userLoginRequestDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (!user.getPassword().equals(userLoginRequestDTO.password())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return userMapper.toDto(user);
    }
}
