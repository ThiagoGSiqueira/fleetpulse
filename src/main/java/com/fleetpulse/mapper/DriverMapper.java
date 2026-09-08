package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.entity.Driver;
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
        driverEntity.setName(driverDto.getName());
        driverEntity.setCnhNumber(driverDto.getCnhNumber());

        return driverEntity;
    }
}
