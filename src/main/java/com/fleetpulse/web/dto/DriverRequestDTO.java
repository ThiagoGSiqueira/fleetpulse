package com.fleetpulse.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DriverRequestDTO(
    @NotBlank @Size(min = 3, max = 40) String name,
    @NotBlank String email,
    @NotBlank String password,
    @NotBlank @Size(min = 6, max = 6) String cnhNumber
) {}