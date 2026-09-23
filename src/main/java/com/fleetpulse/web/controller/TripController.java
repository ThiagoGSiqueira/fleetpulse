package com.fleetpulse.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetpulse.service.TripService;
import com.fleetpulse.usecase.CreateTripUseCase;
import com.fleetpulse.usecase.UpdateTripRouteUseCase;
import com.fleetpulse.web.dto.AssignDriverDTO;
import com.fleetpulse.web.dto.AssignVehicleDTO;
import com.fleetpulse.web.dto.TripRequestDTO;
import com.fleetpulse.web.dto.TripRequestUpdateDTO;
import com.fleetpulse.web.dto.TripResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@RestController 
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;
    private final CreateTripUseCase createTripUseCase;
    private final UpdateTripRouteUseCase updateTripRouteUseCase;

    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(@Valid @RequestBody TripRequestDTO request) {
        TripResponseDTO response = createTripUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TripResponseDTO>> getAllTrips() {
        List<TripResponseDTO> response = tripService.findAllTrips();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDTO> getTripById(@PathVariable Long id) {
        TripResponseDTO response = tripService.findTripById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}/driver")
    public ResponseEntity<TripResponseDTO> reassignDriver(@PathVariable Long id, @Valid @RequestBody AssignDriverDTO request) {
        TripResponseDTO response = tripService.reassignDriver(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<TripResponseDTO> reassignVehicle(@PathVariable Long id, @Valid @RequestBody AssignVehicleDTO request) {
        TripResponseDTO response = tripService.reassignVehicle(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/route")
    public ResponseEntity<TripResponseDTO> updateRoute(@PathVariable Long id, @Valid @RequestBody TripRequestUpdateDTO request) {
        TripResponseDTO response = updateTripRouteUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<TripResponseDTO> cancelTrip(@PathVariable Long id) {
        TripResponseDTO response = tripService.cancelTripById(id);
        return ResponseEntity.ok(response);
    }
}
