package com.fleetpulse.usecase;

import org.springframework.stereotype.Component;

import com.fleetpulse.service.BrasilApiService;
import com.fleetpulse.service.TripService;
import com.fleetpulse.web.dto.AddressData;
import com.fleetpulse.web.dto.TripRequestUpdateDTO;
import com.fleetpulse.web.dto.TripResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Component 
public class UpdateTripRouteUseCase {

    private final BrasilApiService brasilApiService;
    private final TripService tripService;

    public TripResponseDTO execute(Long id, TripRequestUpdateDTO request) {
        AddressData newOriginAddress = request.originZipCode() != null
                ? new AddressData(brasilApiService.searchAddress(request.originZipCode()))
                : null;
        AddressData newDestinationAddress = request.destinationZipCode() != null
                ? new AddressData(brasilApiService.searchAddress(request.destinationZipCode()))
                : null;

        return tripService.updateRoute(id, newOriginAddress, newDestinationAddress);
    }
}
