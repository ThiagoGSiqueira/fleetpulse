package com.fleetpulse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fleetpulse.exception.ResourceAlreadyDisabledException;

public class VehicleTest {
    @Test
    @DisplayName("Should set vehicle status to INACTIVE when deactivated")
    void shouldDeactivateVehicle() {
        Vehicle vehicle = new Vehicle("ABC1234", "Volvo F55");

        vehicle.deactivate();

        assertEquals(VehicleStatus.INACTIVE, vehicle.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when attempting to deactivate an already inactive vehicle")
    void shouldThrowExceptionWhenVehicleIsAlreadyInactive() {
        Vehicle vehicle = new Vehicle("ABC1234", "Volvo F55");
        vehicle.deactivate();

        ResourceAlreadyDisabledException ex = assertThrows(ResourceAlreadyDisabledException.class, () -> {
            vehicle.deactivate();
        });

        assertEquals(String.format("%s with %s '%s' is already deactivated.", "Vehicle", "License Plate", vehicle.getLicensePlate()), ex.getMessage());
    }
}
