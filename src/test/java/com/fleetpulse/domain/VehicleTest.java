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
        Vehicle vehicle = new Vehicle("KGX8841", "Scania R450");
        vehicle.deactivate();

        ResourceAlreadyDisabledException ex = assertThrows(ResourceAlreadyDisabledException.class, () -> {
            vehicle.deactivate();
        });

        assertEquals(String.format("%s with %s '%s' is already deactivated.", "Vehicle", "License Plate",
                vehicle.getLicensePlate()), ex.getMessage());
    }

    @Test
    @DisplayName("Should update model and license plate")
    void shouldUpdateVehicle() {
        Vehicle vehicle = new Vehicle("KGX8841", "Scania R450");

        vehicle.updateVehicleData("BRA2E19", "Mercedes-Benz Actros");

        assertEquals("BRA2E19", vehicle.getLicensePlate());
        assertEquals("Mercedes-Benz Actros", vehicle.getModel());
    }

    @Test
    @DisplayName("Should update license plate and keep original model when new model is null")
    void shouldUpdateOnlyLicensePlateWhenModelIsNull() {
        Vehicle vehicle = new Vehicle("KGX8841", "Scania R450");

        vehicle.updateVehicleData("BRA2E19", null);

        assertEquals("BRA2E19", vehicle.getLicensePlate());
        assertEquals("Scania R450", vehicle.getModel());
    }

    @Test 
    @DisplayName("Should update model and keep original license plate when new license plate is null")
    void shouldUpdateOnlyModelWhenLicensePlateIsNull() {
        Vehicle vehicle = new Vehicle("KGX8841", "Scania R450");

        vehicle.updateVehicleData(null, "Mercedes-Benz Actros");

        assertEquals("KGX8841", vehicle.getLicensePlate());
        assertEquals("Mercedes-Benz Actros", vehicle.getModel());
    }
}
