package com.fleetpulse.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String value) {
        super(String.format("%s with ID: %s not found.", resourceName, value));
    }
}

