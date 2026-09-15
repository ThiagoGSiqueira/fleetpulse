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

@Component
public class TripMapper {
        public TripResponseDto toDto(Trip tripEntity, DriverResponseDto driverDto, VehicleResponseDto vehicleDto) {
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
                Trip tripEntity = new Trip();
                tripEntity.setDriver(driverEntity);
                tripEntity.setVehicle(vehicleEntity);
                tripEntity.setOriginZipCode(tripDto.originZipCode());
                tripEntity.setDestinationZipCode(tripDto.destinationZipCode());
                tripEntity.setOriginAddress(originBrasilApiAddress.getFormattedAddress());
                tripEntity.setOriginLatitude(originBrasilApiAddress.getLatitude());
                tripEntity.setOriginLongitude(originBrasilApiAddress.getLongitude());
                tripEntity.setDestinationAddress(destinationBrasilApiAddress.getFormattedAddress());
                tripEntity.setDestinationLatitude(destinationBrasilApiAddress.getLatitude());
                tripEntity.setDestinationLongitude(destinationBrasilApiAddress.getLongitude());
                tripEntity.setStartTime(LocalDateTime.now());
                tripEntity.setStatus(TripStatus.IN_PROGRESS);
                double distanceInKm = GeoUtils.calculateEstimatedRoadDistanceInKm(originBrasilApiAddress.getLatitude(), originBrasilApiAddress.getLongitude(),
        destinationBrasilApiAddress.getLatitude(), destinationBrasilApiAddress.getLongitude());
                tripEntity.setDistanceInKm(distanceInKm);
                return tripEntity;
        }

}
