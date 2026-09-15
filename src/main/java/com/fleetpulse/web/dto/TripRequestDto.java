package com.fleetpulse.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TripRequestDto(
        @NotNull(message = "Driver ID is required.") 
        Long driverId,
        @NotNull(message = "Vehicle ID is required.") 
        Long vehicleId,
        @Size(min = 8, max = 10)
        @NotBlank(message = "Origin Zip Code is required.") 
        String originZipCode,
        @Size(min = 8, max = 10)
        @NotBlank(message = "Destination Zip Code is required.") 
        String destinationZipCode) {

}
