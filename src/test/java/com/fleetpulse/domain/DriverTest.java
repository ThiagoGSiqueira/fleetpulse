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
        User userDriver = new User("Thiago", "thiago@gmail.com", "thiago", UserRole.ROLE_DRIVER);

        Driver driver = new Driver(userDriver, "123456");

        driver.deactivate();

        assertEquals(DriverStatus.INACTIVE, driver.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when attempting to deactivate an already inactive driver")
    void shouldThrowExceptionWhenDriverIsAlreadyInactive() {
        User userDriver = new User("Thiago", "thiago@gmail.com", "thiago", UserRole.ROLE_DRIVER);

        Driver driver = new Driver(userDriver, "123456");
        driver.deactivate();

        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class, () -> driver.deactivate());

        assertEquals(String.format("Driver with CNH '%s' is inactive. Status: %s", driver.getCnhNumber(),
                driver.getStatus()), ex.getMessage());
    }

    @Test
    @DisplayName("Should update name and CNH number")
    void shouldUpdateDriver() {
        User userDriver = new User("Thiago", "thiago@gmail.com", "thiago", UserRole.ROLE_DRIVER);

        Driver driver = new Driver(userDriver, "123456");

        driver.updatePersonalData("654321");

        assertEquals("654321", driver.getCnhNumber());
    }

    @Test
    @DisplayName("Should keep all original personal data when all inputs are null")
    void shouldNotUpdatePersonalDataWhenAllInputsAreNull() {
        User userDriver = new User("Thiago", "thiago@gmail.com", "thiago", UserRole.ROLE_DRIVER);

        Driver driver = new Driver(userDriver, "123456");

        driver.updatePersonalData(null);

        assertEquals("123456", driver.getCnhNumber());
    }
}
