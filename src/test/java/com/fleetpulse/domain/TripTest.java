package com.fleetpulse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fleetpulse.exception.ConflictException;

public class TripTest {
    @Test 
    @DisplayName("Should set trip status to CANCELED when cancel")
    void shouldCancelTrip() {
        Trip trip = createValidTrip();

        trip.cancel();

        assertEquals(TripStatus.CANCELED, trip.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when attempting to cancel an already canceled trip")
    void shouldThrowExceptionWhenTripIsAlreadyCanceled() {
        Trip trip = createValidTrip();
        ReflectionTestUtils.setField(trip, "id", 1L);
        trip.cancel();

        ConflictException ex = assertThrows(ConflictException.class, () -> {
            trip.cancel();
        });

        assertEquals(String.format("Trip with ID: %s is already cancelled.", trip.getId().toString()), ex.getMessage());
    }


    private Trip createValidTrip() {
         Trip trip = Trip.builder()
        .driver(new Driver("Isabele", "123456")) // ou a instância/mock de Driver que você usar no teste
        .vehicle(new Vehicle("BRA2E19", "Mercedes-Benz Actros"))
        .originZipCode("01310-100")
        .originAddress("Av. Paulista, 1000 - São Paulo, SP")
        .originLatitude(-23.5615)
        .originLongitude(-46.6560)
        .destinationZipCode("04543-011")
        .destinationAddress("Av. Brig. Faria Lima, 3000 - São Paulo, SP")
        .destinationLatitude(-23.5874)
        .destinationLongitude(-46.6818)
        .build();

        return trip;
    }
}
