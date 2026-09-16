package com.fleetpulse.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetpulse.service.TripService;
import com.fleetpulse.web.dto.TripRequestDto;
import com.fleetpulse.web.dto.TripResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@RestController 
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(@Valid @RequestBody TripRequestDto tripDto) {
        TripResponseDto response = tripService.createTrip(tripDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getAllTrips() {
        List<TripResponseDto> response = tripService.findAllTrips();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDto> getTripById(@PathVariable Long id) {
        TripResponseDto response = tripService.findTripById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
