package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.entity.Driver;
import com.fleetpulse.repository.DriverRepository;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Create - Read - Update - Delete
    @Transactional
    public void createDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Transactional(readOnly = true)
    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void updateDriver(Long id, Driver updateD) {
        Driver d = getDriverById(id);
        d.setName(updateD.getName());
        d.setCnhNumber(updateD.getCnhNumber());
        d.setStatus(updateD.getStatus());
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}
