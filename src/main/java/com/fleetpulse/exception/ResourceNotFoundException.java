package com.fleetpulse.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception thrown when a resource not found in the database
     * 
     * @param resourceName Name of entity (e.g., "Vehicle", "Driver")
     * @param value Value that the user tried
     */

    public ResourceNotFoundException(String resourceName, String value) {
        super(String.format("%s with ID: %s not found.", resourceName, value));
    }
}

