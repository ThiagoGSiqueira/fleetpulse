package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.DriverMapper;
import com.fleetpulse.mapper.TripMapper;
import com.fleetpulse.mapper.VehicleMapper;
import com.fleetpulse.repository.TripRepository;
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
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final TripMapper tripMapper;
    private final DriverMapper driverMapper;
    private final VehicleMapper vehicleMapper;
    private final BrasilApiService brasilApiService;

    // Create - Read - Update - Delete

    @Transactional
    public TripResponseDto createTrip(TripRequestDto tripDto) {
        Driver driverEntity = driverService.findDriverEntityById(tripDto.driverId());
        Vehicle vehicleEntity = vehicleService.findVehicleEntityById(tripDto.vehicleId());

        DriverResponseDto driverDto = driverMapper.toDto(driverEntity);
        VehicleResponseDto vehicleDto = vehicleMapper.toDto(vehicleEntity);

        BrasilApiDto originBrasilApiAddress = brasilApiService.searchAddress(tripDto.originZipCode());
        BrasilApiDto destinationBrasilApiAddress = brasilApiService.searchAddress(tripDto.destinationZipCode());

        Trip tripEntity = tripMapper.toEntity(tripDto, driverEntity, vehicleEntity, originBrasilApiAddress,
                destinationBrasilApiAddress);

        tripRepository.save(tripEntity);
        return tripMapper.toDto(tripEntity, driverDto, vehicleDto);
    }

    @Transactional(readOnly = true)
    public List<TripResponseDto> findAllTrips() {
        return tripRepository.findAll().stream()
        .map(tripEntity -> tripMapper.toDto(tripEntity, driverMapper.toDto(tripEntity.getDriver()), vehicleMapper.toDto(tripEntity.getVehicle())))
        .toList();
    }

    @Transactional(readOnly = true)
    public TripResponseDto findTripById(Long id) {
        Trip tripEntity = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip", id.toString()));
        return tripMapper.toDto(tripEntity, driverMapper.toDto(tripEntity.getDriver()), vehicleMapper.toDto(tripEntity.getVehicle()));
    }

    @Transactional
    public void deleteById(Long id) {
        if(!tripRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle", id.toString());
        }
        tripRepository.deleteById(id);
    }
}
