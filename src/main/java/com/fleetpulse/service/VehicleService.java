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
    public VehicleResponseDTO create(VehicleRequestDTO request) {
        Vehicle vehicle = vehicleMapper.toEntity(request);
        if(vehicleRepository.existsByLicensePlate(request.licensePlate())){
            throw new ConflictException(String.format("Vehicle with License Plate '%s' already exists", request.licensePlate()));
        }

        vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findAll() {
        return vehicleRepository.findAll()
        .stream()
        .map(vehicleMapper::toDto)
        .toList();
    }

    @Transactional(readOnly = true)
    public VehicleResponseDTO findById(Long id) {
        Vehicle vehicle = findEntityById(id);
        return vehicleMapper.toDto(vehicle);
    }

    @Transactional
    public VehicleResponseDTO update(Long id, VehicleRequestUpdateDTO request) {
        Vehicle vehicle = findEntityById(id);
        vehicle.updateVehicleData(request.licensePlate(), request.model());

        return vehicleMapper.toDto(vehicle);
    }

    @Transactional 
    public void delete(Long id) {
        Vehicle vehicle = findEntityById(id);
        vehicle.deactivate();
    }

    @Transactional(readOnly = true)
    public Vehicle findEntityById(Long id) {
        return vehicleRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException(String.format("Vehicle with ID: %s not found", id.toString())));
    }

}
