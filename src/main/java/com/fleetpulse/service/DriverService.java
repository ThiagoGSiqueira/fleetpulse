package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.exception.ConflictException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.DriverMapper;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.web.dto.DriverRequestDTO;
import com.fleetpulse.web.dto.DriverRequestUpdateDTO;
import com.fleetpulse.web.dto.DriverResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    // Create - Read - Update - Delete
    @Transactional
    public DriverResponseDTO createDriver(DriverRequestDTO request) {
        if (driverRepository.existsByCnhNumber(request.cnhNumber())) {
            throw new ConflictException(String.format("Driver with CNH '%s' already exists", request.cnhNumber()));
        }

        Driver driver = driverMapper.toEntity(request);
        driverRepository.save(driver);

        return driverMapper.toDto(driver);
    }

    @Transactional(readOnly = true)
    public List<DriverResponseDTO> findAllDrivers() {
        return driverRepository.findAll().stream()
                .map(driverMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DriverResponseDTO findDriverById(Long id) {
        Driver driver = findDriverEntityById(id);

        return driverMapper.toDto(driver);
    }

    @Transactional
    public DriverResponseDTO updateDriver(Long id, DriverRequestUpdateDTO request) {
        Driver driver = findDriverEntityById(id);

        driver.updatePersonalData(request.name(), request.cnhNumber());

        return driverMapper.toDto(driver);
    }

    @Transactional
    public void deleteDriver(Long id) {
        Driver driver = findDriverEntityById(id);
        driver.deactivate();
    }

    @Transactional(readOnly = true)
    public Driver findDriverEntityById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Driver with ID: %s not found", id.toString())));
    }
}
