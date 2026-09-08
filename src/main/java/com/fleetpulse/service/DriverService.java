package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.entity.Driver;
import com.fleetpulse.entity.DriverStatus;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.web.dto.DriverRequestDto;
import com.fleetpulse.web.dto.DriverResponseDto;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Create - Read - Update - Delete
    @Transactional
    public DriverResponseDto createDriver(DriverRequestDto driverDto) {
        Driver driverEntity = new Driver();
        driverEntity.setName(driverDto.getName());
        driverEntity.setCnhNumber(driverDto.getCnhNumber());
        driverEntity.setStatus(DriverStatus.AVAILABLE);
        driverRepository.save(driverEntity);
        return new DriverResponseDto(driverEntity.getId(), driverEntity.getName(), driverEntity.getCnhNumber(), driverEntity.getStatus());
    }

    @Transactional(readOnly = true)
    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Driver findDriverById(Long id) {
        return driverRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void updateDriver(Long id, Driver updateD) {
        Driver d = findDriverById(id);
        d.setName(updateD.getName());
        d.setCnhNumber(updateD.getCnhNumber());
        d.setStatus(updateD.getStatus());
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}
