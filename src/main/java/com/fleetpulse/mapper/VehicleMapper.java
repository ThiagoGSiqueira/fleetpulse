package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.web.dto.VehicleRequestDTO;
import com.fleetpulse.web.dto.VehicleResponseDTO;

@Component
public class VehicleMapper {
    public VehicleResponseDTO toDto(Vehicle vehicleEntity) {
        return new VehicleResponseDTO(vehicleEntity.getId(), vehicleEntity.getLicensePlate(), vehicleEntity.getModel(),
                vehicleEntity.getStatus());
    }

    public Vehicle toEntity(VehicleRequestDTO vehicleDto) {
        Vehicle vehicleEntity = Vehicle.builder()
        .licensePlate(vehicleDto.licensePlate())
        .model(vehicleDto.model())
        .build();

        return vehicleEntity;
    }
}
