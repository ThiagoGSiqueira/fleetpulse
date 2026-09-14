package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.web.dto.TripRequestDto;
import com.fleetpulse.web.dto.TripResponseDto;

@Component
public class TripMapper {
    public TripResponseDto toDto(Trip tripEntity) {
        return new TripResponseDto(tripEntity.getId(), tripEntity.getDriver(), tripEntity.getVehicle(),
                tripEntity.getOriginZipCode(), tripEntity.getOriginAddress(), tripEntity.getDestinationZipCode(),
                tripEntity.getDestinationAddress(), tripEntity.getDistanceInKm(), tripEntity.getStartTime(),
                tripEntity.getStatus());
    }

    public Trip toEntity(TripRequestDto tripDto, Driver driverEntity, Vehicle vehicleEntity) {
        Trip tripEntity = new Trip();
        tripEntity.setDriver(driverEntity);
        tripEntity.setVehicle(vehicleEntity);
        tripEntity.setOriginZipCode(tripDto.OriginZipCode());
        tripEntity.setDestinationZipCode(tripDto.DestinationZipCode());

        return tripEntity;
    }
}

