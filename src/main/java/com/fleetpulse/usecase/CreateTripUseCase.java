package com.fleetpulse.usecase;

import org.springframework.stereotype.Component;

import com.fleetpulse.service.BrasilApiService;
import com.fleetpulse.service.TripService;
import com.fleetpulse.web.dto.BrasilApiDTO;
import com.fleetpulse.web.dto.TripRequestDTO;
import com.fleetpulse.web.dto.TripResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Component 
public class CreateTripUseCase {
    
    private final BrasilApiService brasilApiService;
    private final TripService tripService;

    public TripResponseDTO execute(TripRequestDTO request) {
        BrasilApiDTO originBrasilApiAddress = brasilApiService.searchAddress(request.originZipCode());
        BrasilApiDTO destinationBrasilApiAddress = brasilApiService.searchAddress(request.destinationZipCode());

        return tripService.createTrip(request, originBrasilApiAddress, destinationBrasilApiAddress);
    }
}
