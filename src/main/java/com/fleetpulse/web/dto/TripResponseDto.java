package com.fleetpulse.web.dto;

import java.time.LocalDateTime;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.TripStatus;
import com.fleetpulse.domain.Vehicle;

public record TripResponseDto(
    Long id,
    Driver driver,
    Vehicle vehicle,
    String originZipCode,
    String originAddress,
    String destinationZipCode,
    String destinationAddress,
    Double distanceInKm,
    LocalDateTime startTime,
    TripStatus status
) {

}
