package com.fleetpulse.web.dto;

import com.fleetpulse.domain.VehicleStatus;

public record VehicleResponseDTO(Long id, String licensePlate, String model, double kmSinceLastMaintenance, VehicleStatus status) {

}
