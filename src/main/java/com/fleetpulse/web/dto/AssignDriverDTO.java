package com.fleetpulse.web.dto;

import jakarta.validation.constraints.NotNull;

public record AssignDriverDTO(
    @NotNull 
    Long driverId
) {

}
