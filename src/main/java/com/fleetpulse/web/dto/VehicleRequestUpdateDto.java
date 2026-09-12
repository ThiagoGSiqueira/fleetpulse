package com.fleetpulse.web.dto;

import jakarta.validation.constraints.Size;

public record VehicleRequestUpdateDto(
        @Size(min = 7, max = 7, message = "size must be 7 characters long") 
        String licensePlate,
        @Size(min = 4, max = 40) 
        String model) {
}
