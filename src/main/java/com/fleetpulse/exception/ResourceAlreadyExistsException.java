package com.fleetpulse.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Exception thrown when a resource already exists in the database.
     * 
     * @param resourceName Name of entity (e.g., "Vehicle", "Driver")
     * @param fieldName Name of the duplicated field (e.g., "License Plate", "CNH")
     * @param value Value that the user tried
     */

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String value) {
        super(String.format("%s with %s '%s' already exists.", resourceName, fieldName, value));
    } 
}
