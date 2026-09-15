package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.DriverStatus;
import com.fleetpulse.web.dto.DriverRequestDto;
import com.fleetpulse.web.dto.DriverResponseDto;

@Component
public class DriverMapper {
    public DriverResponseDto toDto(Driver driverEntity) {
        return new DriverResponseDto(driverEntity.getId(), driverEntity.getName(), driverEntity.getCnhNumber(),
                driverEntity.getStatus());
    }

    public Driver toEntity(DriverRequestDto driverDto) {
        Driver driverEntity = new Driver();
        driverEntity.setName(driverDto.name());
        driverEntity.setCnhNumber(driverDto.cnhNumber());
        driverEntity.setStatus(DriverStatus.AVAILABLE);

        return driverEntity;
    }
}
