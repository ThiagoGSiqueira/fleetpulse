package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.Trip;
import com.fleetpulse.domain.Vehicle;
import com.fleetpulse.web.dto.BrasilApiDTO;
import com.fleetpulse.web.dto.DriverResponseDTO;
import com.fleetpulse.web.dto.TripRequestDTO;
import com.fleetpulse.web.dto.TripResponseDTO;
import com.fleetpulse.web.dto.VehicleResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Component
public class TripMapper {

        private final DriverMapper driverMapper;
        private final VehicleMapper vehicleMapper;

        public TripResponseDTO toDto(Trip tripEntity) {

                DriverResponseDTO driverDto = driverMapper.toDto(tripEntity.getDriver());
                VehicleResponseDTO vehicleDto = vehicleMapper.toDto(tripEntity.getVehicle());

                return new TripResponseDTO(tripEntity.getId(), driverDto, vehicleDto,
                                tripEntity.getOriginZipCode(), tripEntity.getOriginAddress(),
                                tripEntity.getDestinationZipCode(),
                                tripEntity.getDestinationAddress(), tripEntity.getDistanceInKm(),
                                tripEntity.getStartTime(),
                                tripEntity.getStatus());
        }

        public Trip toEntity(TripRequestDTO tripDto,
                        Driver driverEntity, Vehicle vehicleEntity,
                        BrasilApiDTO originBrasilApiAddress, BrasilApiDTO destinationBrasilApiAddress) {
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
