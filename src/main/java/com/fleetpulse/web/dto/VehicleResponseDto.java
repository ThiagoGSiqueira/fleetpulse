package com.fleetpulse.web.dto;

import com.fleetpulse.domain.VehicleStatus;

public record VehicleResponseDto(Long id, String licensePlate, String model, VehicleStatus status) {

}
