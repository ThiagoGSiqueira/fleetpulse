package com.fleetpulse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.DriverMapper;
import com.fleetpulse.mapper.TripMapper;
import com.fleetpulse.mapper.VehicleMapper;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.repository.TripRepository;
import com.fleetpulse.repository.VehicleRepository;
import com.fleetpulse.web.dto.BrasilApiDto;
import com.fleetpulse.web.dto.DriverResponseDto;
import com.fleetpulse.web.dto.TripRequestDto;
import com.fleetpulse.web.dto.TripResponseDto;
import com.fleetpulse.web.dto.VehicleResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final TripMapper tripMapper;
    private final DriverMapper driverMapper;
    private final VehicleMapper vehicleMapper;
    private final BrasilApiService brasilApiService;

    @Transactional
    public TripResponseDto createTrip(TripRequestDto tripDto) {
        Driver driverEntity = driverRepository.findById(tripDto.driverId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver", tripDto.driverId().toString()));
        Vehicle vehicleEntity = vehicleRepository.findById(tripDto.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", tripDto.vehicleId().toString()));

        DriverResponseDto driverDto = driverMapper.toDto(driverEntity);
        VehicleResponseDto vehicleDto = vehicleMapper.toDto(vehicleEntity);

        BrasilApiDto originBrasilApiAddress = brasilApiService.searchAddress(tripDto.originZipCode());
        BrasilApiDto destinationBrasilApiAddress = brasilApiService.searchAddress(tripDto.destinationZipCode());
        
        Trip tripEntity = tripMapper.toEntity(tripDto, driverEntity, vehicleEntity, originBrasilApiAddress, destinationBrasilApiAddress);
        
        

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity, driverDto, vehicleDto);
    }
}
