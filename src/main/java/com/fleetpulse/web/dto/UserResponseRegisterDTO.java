package com.fleetpulse.web.dto;

import com.fleetpulse.domain.UserRole;

public record UserResponseRegisterDTO(Long id, String name, String email, UserRole role) {

}
