package com.fleetpulse.exception;

public class ResourceAlreadyDisabledException extends RuntimeException{
    public ResourceAlreadyDisabledException(String message) {
        super(message);
    }

    public ResourceAlreadyDisabledException(String resourceName, String fieldName, String value) {
        super(String.format("%s with %s '%s' is already deactivated.", resourceName, fieldName, value));
    } 
}
