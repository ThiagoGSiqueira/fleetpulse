package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.domain.VehicleStatus;
import com.fleetpulse.exception.ResourceAlreadyExistsException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.VehicleMapper;
import com.fleetpulse.repository.VehicleRepository;
import com.fleetpulse.web.dto.VehicleRequestDto;
import com.fleetpulse.web.dto.VehicleRequestUpdateDto;
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
        Vehicle vehicleEntity = findVehicleEntityById(id);
        return vehicleMapper.toDto(vehicleEntity);
    }

    @Transactional
    public VehicleResponseDto updateVehicle(Long id, VehicleRequestUpdateDto vehicleDto) {
        Vehicle vehicleEntity = findVehicleEntityById(id);
        vehicleEntity.updateVehicleData(vehicleDto.licensePlate(), vehicleDto.model());
        vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toDto(vehicleEntity);
    }

    @Transactional 
    public void deleteVehicle(Long id) {
        Vehicle vehicleEntity = findVehicleEntityById(id);
        vehicleEntity.deactivate();
        vehicleRepository.save(vehicleEntity);
    }

    @Transactional(readOnly = true)
    public Vehicle findVehicleEntityById(Long id) {
        return vehicleRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException("Vehicle", id.toString()));
    }

}
