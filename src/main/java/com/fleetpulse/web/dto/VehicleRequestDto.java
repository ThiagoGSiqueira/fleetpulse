package com.fleetpulse.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VehicleRequestDto(
    @NotBlank(message = "License plate is required.")
    @Size(min = 7, max = 7, message = "size must be 7 characters long")
    String licensePlate,
    @NotBlank(message = "Model is required.")
    @Size(min = 4, max = 40)
    String model
) {

}
