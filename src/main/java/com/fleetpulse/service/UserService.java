package com.fleetpulse.service;

import org.springframework.stereotype.Service;

import com.fleetpulse.domain.User;
import com.fleetpulse.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 

@Service 
public class UserService {
    private final UserRepository userRepository;
}
