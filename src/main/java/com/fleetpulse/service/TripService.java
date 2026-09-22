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
    private final BrasilApiService brasilApiService;

    // Create - Read - Update - Delete

    @Transactional
    public TripResponseDTO createTrip(TripRequestDTO tripDto) {
        Driver driverEntity = driverService.findDriverEntityById(tripDto.driverId());
        Vehicle vehicleEntity = vehicleService.findVehicleEntityById(tripDto.vehicleId());

        driverEntity.assignToTrip();
        vehicleEntity.assignToTrip();

        BrasilApiDTO originBrasilApiAddress = brasilApiService.searchAddress(tripDto.originZipCode());
        BrasilApiDTO destinationBrasilApiAddress = brasilApiService.searchAddress(tripDto.destinationZipCode());

        Trip tripEntity = tripMapper.toEntity(tripDto, driverEntity, vehicleEntity, originBrasilApiAddress,
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
        Trip tripEntity = findTripEntityById(id);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional 
    public TripResponseDTO reassignDriver(Long id, AssignDriverDTO driverDto) {
        Trip tripEntity = findTripEntityById(id);
        Driver driverEntity = driverService.findDriverEntityById(driverDto.driverId());
        tripEntity.reassignDriver(driverEntity);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional
    public TripResponseDTO cancelTripById(Long id) {
        Trip tripEntity = findTripEntityById(id);
        tripEntity.cancel();
        
        return tripMapper.toDto(tripEntity);
    }

    @Transactional(readOnly = true) 
    public Trip findTripEntityById(Long id) {
        return tripRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Trip with %s not found", id.toString())));
    }
}
