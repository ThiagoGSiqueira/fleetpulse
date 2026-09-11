package com.fleetpulse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.domain.VehicleStatus;
import com.fleetpulse.mapper.VehicleMapper;
import com.fleetpulse.repository.VehicleRepository;
import com.fleetpulse.web.dto.VehicleRequestDto;
import com.fleetpulse.web.dto.VehicleResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    // Create - Read - Update - Delete

    @Transactional
    public VehicleResponseDto createVehicle(@Valid @RequestBody VehicleRequestDto vehicleDto) {
        Vehicle vehicleEntity = vehicleMapper.toEntity(vehicleDto);

        vehicleEntity.setStatus(VehicleStatus.AVAILABLE);
        vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toDto(vehicleEntity);
    }
}
