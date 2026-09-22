package com.fleetpulse.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DriverRequestDTO(
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 40)
    String name, 
    @NotBlank(message = "CNH is required.")
    @Size(min = 6, max = 6, message = "size must be 6 characters long")
    String cnhNumber
) {}
