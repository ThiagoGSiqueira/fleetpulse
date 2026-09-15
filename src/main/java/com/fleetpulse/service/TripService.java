package com.fleetpulse.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.TripStatus;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.TripMapper;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.repository.TripRepository;
import com.fleetpulse.repository.VehicleRepository;
import com.fleetpulse.util.GeoUtils;
import com.fleetpulse.web.dto.BrasilApiDto;
import com.fleetpulse.web.dto.TripRequestDto;
import com.fleetpulse.web.dto.TripResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final TripMapper tripMapper;
    private final BrasilApiService brasilApiService;

    @Transactional
    public TripResponseDto createTrip(TripRequestDto tripDto) {
        Driver driverEntity = driverRepository.findById(tripDto.driverId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver", tripDto.driverId().toString()));
        Vehicle vehicleEntity = vehicleRepository.findById(tripDto.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", tripDto.vehicleId().toString()));
        BrasilApiDto originBrasilApiAddress = brasilApiService.searchAddress(tripDto.OriginZipCode());
        BrasilApiDto destinationBrasilApiAddress = brasilApiService.searchAddress(tripDto.DestinationZipCode());
        Trip tripEntity = tripMapper.toEntity(tripDto, driverEntity, vehicleEntity);
        tripEntity.setStartTime(LocalDateTime.now());
        tripEntity.setStatus(TripStatus.IN_PROGRESS);
        tripEntity.setOriginAddress(String.format("%s, %s, %s - %s, %s", originBrasilApiAddress.street(),
                originBrasilApiAddress.neighborhood(), originBrasilApiAddress.city(), originBrasilApiAddress.state(),
                originBrasilApiAddress.cep()));
        tripEntity.setOriginLatitude(Double.parseDouble(originBrasilApiAddress.location().coordinates().latitude()));
        tripEntity.setOriginLongitude(Double.parseDouble(originBrasilApiAddress.location().coordinates().longitude()));
        // Destination
        tripEntity.setDestinationAddress(String.format("%s, %s, %s - %s, %s", destinationBrasilApiAddress.street(),
                destinationBrasilApiAddress.neighborhood(), destinationBrasilApiAddress.city(),
                destinationBrasilApiAddress.state(), destinationBrasilApiAddress.cep()));
        tripEntity.setDestinationLatitude(
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().latitude()));
        tripEntity.setDestinationLongitude(
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().longitude()));
        double distanceInKm = GeoUtils.calculateEstimatedRoadDistanceInKm(
                Double.parseDouble(originBrasilApiAddress.location().coordinates().latitude()),
                Double.parseDouble(originBrasilApiAddress.location().coordinates().longitude()),
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().latitude()),
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().longitude()));
        tripEntity.setDistanceInKm(distanceInKm);

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity);
    }
}
