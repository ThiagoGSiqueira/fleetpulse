package com.fleetpulse.web.dto;

import com.fleetpulse.entity.DriverStatus;

public record DriverResponseDto(Long id, String name, String cnhNumber, DriverStatus status) {

}
