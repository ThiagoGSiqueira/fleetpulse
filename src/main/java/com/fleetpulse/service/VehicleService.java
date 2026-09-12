package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.domain.VehicleStatus;
import com.fleetpulse.exception.ResourceAlreadyExistsException;
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
        if(vehicleRepository.existsByLicensePlate(vehicleDto.licensePlate())){
            throw new ResourceAlreadyExistsException("Vehicle", "License Plate", vehicleDto.licensePlate());
        }
        vehicleEntity.setStatus(VehicleStatus.AVAILABLE);
        vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toDto(vehicleEntity);
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDto> findAllVehicles() {
        return vehicleRepository.findAll()
        .stream()
        .map(vehicle -> vehicleMapper.toDto(vehicle))
        .toList();
    }

    @Transactional(readOnly = true)
    public VehicleResponseDto findVehicleById(Long id) {
        Vehicle vehicleEntity = vehicleRepository.findById(id).orElseThrow();
        return vehicleMapper.toDto(vehicleEntity);
    }
}
