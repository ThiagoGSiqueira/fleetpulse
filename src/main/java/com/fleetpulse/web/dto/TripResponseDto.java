package com.fleetpulse.web.dto;

import java.time.LocalDateTime;

import com.fleetpulse.domain.TripStatus;

public record TripResponseDto(
    Long id,
    DriverResponseDto driverDto,
    VehicleResponseDto vehicleDto,
    String originZipCode,
    String originAddress,
    String destinationZipCode,
    String destinationAddress,
    Double distanceInKm,
    LocalDateTime startTime,
    TripStatus status
) {

}
