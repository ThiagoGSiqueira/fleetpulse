package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.TripMapper;
import com.fleetpulse.repository.TripRepository;
import com.fleetpulse.web.dto.AssignDriverDTO;
import com.fleetpulse.web.dto.AssignVehicleDTO;
import com.fleetpulse.web.dto.BrasilApiDTO;
import com.fleetpulse.web.dto.TripRequestDTO;
import com.fleetpulse.web.dto.TripResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final TripMapper tripMapper;
    private final BrasilApiService brasilApiService;

    // Create - Read - Update - Delete

    @Transactional
    public TripResponseDTO createTrip(TripRequestDTO request) {
        Driver driver = driverService.findDriverEntityById(request.driverId());
        Vehicle vehicle = vehicleService.findVehicleEntityById(request.vehicleId());

        driver.assignToTrip();
        vehicle.assignToTrip();

        BrasilApiDTO originBrasilApiAddress = brasilApiService.searchAddress(request.originZipCode());
        BrasilApiDTO destinationBrasilApiAddress = brasilApiService.searchAddress(request.destinationZipCode());

        Trip tripEntity = tripMapper.toEntity(request, driver, vehicle, originBrasilApiAddress,
                destinationBrasilApiAddress);

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional(readOnly = true)
    public List<TripResponseDTO> findAllTrips() {
        return tripRepository.findAll().stream()
        .map(tripMapper::toDto)
        .toList();
    }

    @Transactional(readOnly = true)
    public TripResponseDTO findTripById(Long id) {
        Trip trip = findTripEntityById(id);
        return tripMapper.toDto(trip);
    }

    @Transactional 
    public TripResponseDTO reassignDriver(Long id, AssignDriverDTO request) {
        Trip trip = findTripEntityById(id);
        Driver driver = driverService.findDriverEntityById(request.driverId());
        trip.reassignDriver(driver);
    
        return tripMapper.toDto(trip);
    }

    @Transactional 
    public TripResponseDTO reassignVehicle(Long id, AssignVehicleDTO request) {
        Trip trip = findTripEntityById(id);
        Vehicle vehicle = vehicleService.findVehicleEntityById(request.vehicleId());
        trip.reassignVehicle(vehicle);

        return tripMapper.toDto(trip);
    }

    @Transactional
    public TripResponseDTO cancelTripById(Long id) {
        Trip trip = findTripEntityById(id);
        trip.cancel();
        
        return tripMapper.toDto(trip);
    }

    @Transactional(readOnly = true) 
    public Trip findTripEntityById(Long id) {
        return tripRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Trip with %s not found", id.toString())));
    }

}
