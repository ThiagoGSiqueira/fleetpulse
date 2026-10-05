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
import com.fleetpulse.web.dto.AddressData;
import com.fleetpulse.web.dto.AssignDriverDTO;
import com.fleetpulse.web.dto.AssignVehicleDTO;
import com.fleetpulse.web.dto.BrasilApiDTO;
import com.fleetpulse.web.dto.TripRequestDTO;
import com.fleetpulse.web.dto.TripResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final TripMapper tripMapper;

    // Create - Read - Update - Delete

    @Transactional
    public TripResponseDTO create(TripRequestDTO request, BrasilApiDTO originBrasilApiAddress, BrasilApiDTO destinationBrasilApiAddress) {
        Driver driver = driverService.findEntityById(request.driverId());
        Vehicle vehicle = vehicleService.findEntityById(request.vehicleId());

        driver.assignToTrip();
        vehicle.assignToTrip();

        Trip tripEntity = tripMapper.toEntity(request, driver, vehicle, originBrasilApiAddress,
                destinationBrasilApiAddress);

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional(readOnly = true)
    public List<TripResponseDTO> findAll() {
        return tripRepository.findAll().stream()
                .map(tripMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TripResponseDTO findById(Long id) {
        Trip trip = findEntityById(id);
        return tripMapper.toDto(trip);
    }

    @Transactional
    public TripResponseDTO reassignDriver(Long id, AssignDriverDTO request) {
        Trip trip = findEntityById(id);

        Driver driver = driverService.findEntityById(request.driverId());
        trip.reassignDriver(driver);

        return tripMapper.toDto(trip);
    }

    @Transactional
    public TripResponseDTO reassignVehicle(Long id, AssignVehicleDTO request) {
        Trip trip = findEntityById(id);

        Vehicle vehicle = vehicleService.findEntityById(request.vehicleId());
        trip.reassignVehicle(vehicle);

        return tripMapper.toDto(trip);
    }

    @Transactional
    public TripResponseDTO updateRoute(Long id, AddressData newOriginAddress, AddressData newDestinationAddress) {
        Trip trip = findEntityById(id);

        trip.updateRoute(newOriginAddress, newDestinationAddress);
        return tripMapper.toDto(trip);
    }

    @Transactional
    public TripResponseDTO cancel(Long id) {
        Trip trip = findEntityById(id);
        trip.cancel();

        return tripMapper.toDto(trip);
    }

    @Transactional
    public TripResponseDTO start(Long id) {
        Trip trip = findEntityById(id);
        trip.start();

        return tripMapper.toDto(trip);
    }

    @Transactional 
    public TripResponseDTO complete(Long id) {
        Trip trip = findEntityById(id);
        trip.complete();

        return tripMapper.toDto(trip);
    }

    @Transactional(readOnly = true)
    public Trip findEntityById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Trip with %s not found", id.toString())));
    }

}
