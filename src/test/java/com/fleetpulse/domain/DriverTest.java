package com.fleetpulse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fleetpulse.exception.ResourceAlreadyDisabledException;

public class DriverTest {
    @Test 
    @DisplayName("Should set driver status to INACTIVE when deactivated")
    void shouldDeactivateDriver() {
        Driver driver = new Driver("Thiago", "123456");

        driver.deactivate();

        assertEquals(DriverStatus.INACTIVE, driver.getStatus());
    }

    @Test 
    @DisplayName("Should throw exception when attempting to deactivate an already inactive driver")
    void shouldThrowExceptionWhenDriverIsAlreadyInactive() {
        Driver driver = new Driver("Isabele", "123456");
        driver.deactivate();

        ResourceAlreadyDisabledException ex = assertThrows(
            ResourceAlreadyDisabledException.class, () -> driver.deactivate()
        );

        assertEquals(String.format("%s with %s '%s' is already deactivated.", "Driver", "CNH", driver.getCnhNumber()), ex.getMessage());
    }
}
