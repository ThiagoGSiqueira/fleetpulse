package com.fleetpulse.exception;

public class DriverCannotBeAssignedException extends RuntimeException {
    public DriverCannotBeAssignedException(String message) {
        super(message);
    }

    public DriverCannotBeAssignedException(String resourceName, String fieldName, String value) {
        super(String.format("%s with %s '%s' cannot be assigned to a trip.", resourceName, fieldName, value));
    }
}
