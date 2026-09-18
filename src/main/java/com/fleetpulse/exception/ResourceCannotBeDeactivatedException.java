package com.fleetpulse.exception;

public class ResourceCannotBeDeactivatedException extends RuntimeException{
    public ResourceCannotBeDeactivatedException(String message) {
        super(message);
    }

    public ResourceCannotBeDeactivatedException(String resourceName, String fieldName, String value) {
        super(String.format("%s with %s '%s' cannot be deactivate", resourceName, fieldName, value));
    }
}
