package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.web.dto.DriverRequestDTO;
import com.fleetpulse.web.dto.DriverResponseDTO;

@Component
public class DriverMapper {
    public DriverResponseDTO toDto(Driver driverEntity) {
        return new DriverResponseDTO(driverEntity.getId(), driverEntity.getName(), driverEntity.getCnhNumber(),
                driverEntity.getStatus());
    }

    public Driver toEntity(DriverRequestDTO driverDto) {
        Driver driverEntity = Driver.builder()
        .name(driverDto.name())
        .cnhNumber(driverDto.cnhNumber())
        .build();

        return driverEntity;
    }
}
