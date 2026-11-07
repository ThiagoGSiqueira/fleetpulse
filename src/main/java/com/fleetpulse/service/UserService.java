package com.fleetpulse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.User;
import com.fleetpulse.exception.InvalidCredentialsException;
import com.fleetpulse.exception.ResourceNotFoundException;
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

    @Transactional
    public UserResponseRegisterDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {
        User user = userMapper.toEntity(userRegisterRequestDTO);
        userRepository.save(user);
        return userMapper.toRegisterDto(user);
    }

    @Transactional
    public UserResponseLoginDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        User user = userRepository.findByEmail(userLoginRequestDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (!user.getPassword().equals(userLoginRequestDTO.password())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        String token = tokenService.generateToken(user);

        return userMapper.toLoginDto(user, token);
    }
}
