package com.fleetpulse.web.controller;

import com.fleetpulse.service.UserService;
import com.fleetpulse.web.dto.UserRegisterRequestDTO;
import com.fleetpulse.web.dto.UserResponseDTO;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor 
@RestController 
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        UserResponseDTO response = userService.register(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
