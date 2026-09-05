package com.fleetpulse.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fleetpulse.entity.Driver;
import com.fleetpulse.service.DriverService;
import com.fleetpulse.web.dto.DriverResponseDto;

import jakarta.validation.Valid;

@RestController
public class DriverController {
    
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/driver")
    public ResponseEntity createDriver(@Valid @RequestBody Driver d) {
        driverService.createDriver(d);
        return ResponseEntity.status(201).body(null);
    }

    @GetMapping("/driver")
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        List<DriverResponseDto> driversDto = driverService.findAllDrivers().stream()
        .map(driver -> new DriverResponseDto(driver.getName(), driver.getCnhNumber(), driver.getStatus()))
        .toList();
        return ResponseEntity.ok(driversDto);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable Long id) {
        Driver d = driverService.findDriverById(id);
        return ResponseEntity.ok(new DriverResponseDto(d.getName(), d.getCnhNumber(), d.getStatus()));
    }
}
 