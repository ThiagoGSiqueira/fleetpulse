package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.exception.ConflictException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.VehicleMapper;
import com.fleetpulse.repository.VehicleRepository;
import com.fleetpulse.web.dto.VehicleRequestDTO;
import com.fleetpulse.web.dto.VehicleRequestUpdateDTO;
import com.fleetpulse.web.dto.VehicleResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    // Create - Read - Update - Delete

    @Transactional
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleDto) {
        Vehicle vehicleEntity = vehicleMapper.toEntity(vehicleDto);
        if(vehicleRepository.existsByLicensePlate(vehicleDto.licensePlate())){
            throw new ConflictException(String.format("Vehicle with License Plate '%s' already exists", vehicleDto.licensePlate()));
        }
        vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toDto(vehicleEntity);
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findAllVehicles() {
        return vehicleRepository.findAll()
        .stream()
        .map(vehicleMapper::toDto)
        .toList();
    }

    @Transactional(readOnly = true)
    public VehicleResponseDTO findVehicleById(Long id) {
        Vehicle vehicleEntity = findVehicleEntityById(id);
        return vehicleMapper.toDto(vehicleEntity);
    }

    @Transactional
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestUpdateDTO vehicleDto) {
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
        orElseThrow(() -> new ResourceNotFoundException(String.format("Vehicle with ID: %s not found", id.toString())));
    }

}
