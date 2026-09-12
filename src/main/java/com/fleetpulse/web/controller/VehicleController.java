package com.fleetpulse.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetpulse.service.VehicleService;
import com.fleetpulse.web.dto.VehicleRequestDto;
import com.fleetpulse.web.dto.VehicleResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody VehicleRequestDto vehicleDto) {
        VehicleResponseDto response = vehicleService.createVehicle(vehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } 

    @GetMapping 
    public ResponseEntity<List<VehicleResponseDto>> getAllVehicles() {
        List<VehicleResponseDto> response = vehicleService.findAllVehicles();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable Long id) {
        VehicleResponseDto response = vehicleService.findVehicleById(id);
        return ResponseEntity.ok(response);
    }
}
