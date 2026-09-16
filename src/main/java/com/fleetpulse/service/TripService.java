package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.TripStatus;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.TripMapper;
import com.fleetpulse.repository.TripRepository;
import com.fleetpulse.web.dto.BrasilApiDto;
import com.fleetpulse.web.dto.TripRequestDto;
import com.fleetpulse.web.dto.TripResponseDto;

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
    public TripResponseDto createTrip(TripRequestDto tripDto) {
        Driver driverEntity = driverService.findDriverEntityById(tripDto.driverId());
        Vehicle vehicleEntity = vehicleService.findVehicleEntityById(tripDto.vehicleId());

        BrasilApiDto originBrasilApiAddress = brasilApiService.searchAddress(tripDto.originZipCode());
        BrasilApiDto destinationBrasilApiAddress = brasilApiService.searchAddress(tripDto.destinationZipCode());

        Trip tripEntity = tripMapper.toEntity(tripDto, driverEntity, vehicleEntity, originBrasilApiAddress,
                destinationBrasilApiAddress);

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional(readOnly = true)
    public List<TripResponseDto> findAllTrips() {
        return tripRepository.findAll().stream()
        .map(tripEntity -> tripMapper.toDto(tripEntity))
        .toList();
    }

    @Transactional(readOnly = true)
    public TripResponseDto findTripById(Long id) {
        Trip tripEntity = findTripEntityById(id);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional
    public TripResponseDto cancelTripById(Long id) {
        Trip tripEntity = findTripEntityById(id);
        tripEntity.setStatus(TripStatus.CANCELED);
        return tripMapper.toDto(tripEntity);
    }

    @Transactional(readOnly = true) 
    public Trip findTripEntityById(Long id) {
        return tripRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Trip", id.toString()));
    }
}
