package com.fleetpulse.web.dto;

import java.time.LocalDateTime;

import com.fleetpulse.domain.TripStatus;

public record TripResponseDTO(
    Long id,
    DriverResponseDTO driver,
    VehicleResponseDTO vehicle,
    String originZipCode,
    String originAddress,
    String destinationZipCode,
    String destinationAddress,
    Double distanceInKm,
    LocalDateTime startTime,
    TripStatus status
) {

}
