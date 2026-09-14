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

    @Transactional 
    public TripResponseDto createTrip(TripRequestDto tripDto) {
        Driver driverEntity = driverRepository.findById(tripDto.driverId()).orElseThrow(() -> new ResourceNotFoundException("Driver", tripDto.driverId().toString()));
        Vehicle vehicleEntity = vehicleRepository.findById(tripDto.vehicleId()).orElseThrow(() -> new ResourceNotFoundException("Vehicle", tripDto.vehicleId().toString()));
        Trip tripEntity = tripMapper.toEntity(tripDto, driverEntity, vehicleEntity);
        tripEntity.setStartTime(LocalDateTime.now());
        tripEntity.setStatus(TripStatus.IN_PROGRESS);

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity);
    }
}
