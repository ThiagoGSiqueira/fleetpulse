package com.fleetpulse.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.TripStatus;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.util.GeoUtils;
import com.fleetpulse.web.dto.BrasilApiDto;
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

    public Trip toEntity(TripRequestDto tripDto,
            Driver driverEntity, Vehicle vehicleEntity,
            BrasilApiDto originBrasilApiAddress, BrasilApiDto destinationBrasilApiAddress) {
        Trip tripEntity = new Trip();
        tripEntity.setDriver(driverEntity);
        tripEntity.setVehicle(vehicleEntity);
        tripEntity.setOriginZipCode(tripDto.OriginZipCode());
        tripEntity.setDestinationZipCode(tripDto.DestinationZipCode());
        tripEntity.setOriginAddress(String.format("%s, %s, %s - %s, %s", originBrasilApiAddress.street(),
                originBrasilApiAddress.neighborhood(), originBrasilApiAddress.city(), originBrasilApiAddress.state(),
                originBrasilApiAddress.cep()));
        tripEntity.setOriginLatitude(Double.parseDouble(originBrasilApiAddress.location().coordinates().latitude()));
        tripEntity.setOriginLongitude(Double.parseDouble(originBrasilApiAddress.location().coordinates().longitude()));
        tripEntity.setDestinationAddress(String.format("%s, %s, %s - %s, %s", destinationBrasilApiAddress.street(),
                destinationBrasilApiAddress.neighborhood(), destinationBrasilApiAddress.city(),
                destinationBrasilApiAddress.state(), destinationBrasilApiAddress.cep()));
        tripEntity.setDestinationLatitude(
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().latitude()));
        tripEntity.setDestinationLongitude(
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().longitude()));
        tripEntity.setStartTime(LocalDateTime.now());
        tripEntity.setStatus(TripStatus.IN_PROGRESS);
        double distanceInKm = GeoUtils.calculateEstimatedRoadDistanceInKm(
                Double.parseDouble(originBrasilApiAddress.location().coordinates().latitude()),
                Double.parseDouble(originBrasilApiAddress.location().coordinates().longitude()),
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().latitude()),
                Double.parseDouble(destinationBrasilApiAddress.location().coordinates().longitude()));
        tripEntity.setDistanceInKm(distanceInKm);
        return tripEntity;
    }

}
