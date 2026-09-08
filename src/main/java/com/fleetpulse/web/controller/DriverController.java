package com.fleetpulse.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fleetpulse.service.DriverService;
import com.fleetpulse.web.dto.DriverRequestDto;
import com.fleetpulse.web.dto.DriverResponseDto;

import jakarta.validation.Valid;

@RestController

public class DriverController {
    
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    
    @PostMapping("/driver")
    public ResponseEntity<DriverResponseDto> createDriver(@Valid @RequestBody DriverRequestDto driverDto) {
        DriverResponseDto response = driverService.createDriver(driverDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/driver")
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        List<DriverResponseDto> response = driverService.findAllDrivers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable Long id) {
        DriverResponseDto response = driverService.findDriverById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/driver/{id}")
    public ResponseEntity<DriverResponseDto> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequestDto driverDto) {
        DriverResponseDto response = driverService.updateDriver(id, driverDto);
        return ResponseEntity.ok(response);
    }
}
