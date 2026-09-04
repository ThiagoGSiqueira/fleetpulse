package com.fleetpulse.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetpulse.entity.Driver;
import com.fleetpulse.service.DriverService;

@RestController
public class DriverController {
    
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping("/driver")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.findAllDrivers());
    }
}
