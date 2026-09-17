package com.fleetpulse.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.TripStatus;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.util.GeoUtils;
import com.fleetpulse.web.dto.BrasilApiDto;
import com.fleetpulse.web.dto.DriverResponseDto;
import com.fleetpulse.web.dto.TripRequestDto;
import com.fleetpulse.web.dto.TripResponseDto;
import com.fleetpulse.web.dto.VehicleResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Component
public class TripMapper {

        private final DriverMapper driverMapper;
        private final VehicleMapper vehicleMapper;

        public TripResponseDto toDto(Trip tripEntity) {

                DriverResponseDto driverDto = driverMapper.toDto(tripEntity.getDriver());
                VehicleResponseDto vehicleDto = vehicleMapper.toDto(tripEntity.getVehicle());

                return new TripResponseDto(tripEntity.getId(), driverDto, vehicleDto,
                                tripEntity.getOriginZipCode(), tripEntity.getOriginAddress(),
                                tripEntity.getDestinationZipCode(),
                                tripEntity.getDestinationAddress(), tripEntity.getDistanceInKm(),
                                tripEntity.getStartTime(),
                                tripEntity.getStatus());
        }

        public Trip toEntity(TripRequestDto tripDto,
                        Driver driverEntity, Vehicle vehicleEntity,
                        BrasilApiDto originBrasilApiAddress, BrasilApiDto destinationBrasilApiAddress) {
                Trip tripEntity = Trip.builder()
                .driver(driverEntity)
                .vehicle(vehicleEntity)
                .originZipCode(tripDto.originZipCode())
                .destinationZipCode(tripDto.destinationZipCode())
                .originAddress(originBrasilApiAddress.getFormattedAddress())
                .originLatitude(originBrasilApiAddress.getLatitude())
                .originLongitude(originBrasilApiAddress.getLongitude())
                .destinationAddress(destinationBrasilApiAddress.getFormattedAddress())
                .destinationLatitude(destinationBrasilApiAddress.getLatitude())
                .destinationLongitude(destinationBrasilApiAddress.getLongitude())
                .build();
                
                return tripEntity;
        }

}
