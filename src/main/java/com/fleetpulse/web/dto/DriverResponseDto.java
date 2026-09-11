package com.fleetpulse.web.dto;

import com.fleetpulse.domain.DriverStatus;

public record DriverResponseDto(Long id, String name, String cnhNumber, DriverStatus status) {

}
