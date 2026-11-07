package com.fleetpulse.web.controller;

import com.fleetpulse.service.UserService;
import com.fleetpulse.web.dto.UserLoginRequestDTO;
import com.fleetpulse.web.dto.UserRegisterRequestDTO;
import com.fleetpulse.web.dto.UserResponseLoginDTO;
import com.fleetpulse.web.dto.UserResponseRegisterDTO;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor 
@RestController 
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // só ADMIN cria usuários; o primeiro admin nasce no AdminSeeder
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<UserResponseRegisterDTO> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        UserResponseRegisterDTO response = userService.register(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseLoginDTO> login(@Valid @RequestBody UserLoginRequestDTO request) {
        UserResponseLoginDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
