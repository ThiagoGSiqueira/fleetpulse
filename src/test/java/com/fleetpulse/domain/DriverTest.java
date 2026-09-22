package com.fleetpulse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fleetpulse.exception.BusinessRuleException;

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

        BusinessRuleException ex = assertThrows(
            BusinessRuleException.class, () -> driver.deactivate()
        );

        assertEquals(String.format("Driver with CNH '%s' is inactive.", driver.getCnhNumber()), ex.getMessage());
    }

    @Test
    @DisplayName("Should update name and CNH number")
    void shouldUpdateDriver() {
        Driver driver = new Driver("Isabele", "123456");

        driver.updatePersonalData("Thiago", "654321");
        assertEquals("Thiago", driver.getName());

        assertEquals("654321", driver.getCnhNumber());
    }

    @Test
    @DisplayName("Should update name and keep original CNH when new CNH is null")
    void shouldUpdateOnlyNameWhenCnhIsNull() {
        Driver driver = new Driver("Isabele", "123456");

        driver.updatePersonalData("Thiago", null);

        assertEquals("Thiago", driver.getName());
        assertEquals("123456", driver.getCnhNumber());
    }

    @Test 
    @DisplayName("Should update CNH and keep original name when new name is null")
    void shouldUpdateOnlyCnhWhenNameIsNull() {
        Driver driver = new Driver("Isabele", "123456");

        driver.updatePersonalData(null, "654321");
        
        assertEquals("Isabele", driver.getName());
        assertEquals("654321", driver.getCnhNumber());
    }

    @Test
    @DisplayName("Should keep all original personal data when all inputs are null")
    void shouldNotUpdatePersonalDataWhenAllInputsAreNull() {
        Driver driver = new Driver("Isabele", "123456");

        driver.updatePersonalData(null, null);

        assertEquals("Isabele", driver.getName());
        assertEquals("123456", driver.getCnhNumber());
    }
}
