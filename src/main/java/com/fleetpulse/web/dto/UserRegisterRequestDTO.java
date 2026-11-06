package com.fleetpulse.web.dto;

import com.fleetpulse.domain.UserRole;

public record UserRegisterRequestDTO(String name, String email, String password, UserRole role) {

}
