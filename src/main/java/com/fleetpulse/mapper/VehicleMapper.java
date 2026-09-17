package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.web.dto.VehicleRequestDto;
import com.fleetpulse.web.dto.VehicleResponseDto;

@Component
public class VehicleMapper {
    public VehicleResponseDto toDto(Vehicle vehicleEntity) {
        return new VehicleResponseDto(vehicleEntity.getId(), vehicleEntity.getLicensePlate(), vehicleEntity.getModel(),
                vehicleEntity.getStatus());
    }

    public Vehicle toEntity(VehicleRequestDto vehicleDto) {
        Vehicle vehicleEntity = Vehicle.builder()
        .licensePlate(vehicleDto.licensePlate())
        .model(vehicleDto.model())
        .build();

        return vehicleEntity;
    }
}
