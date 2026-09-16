package com.fleetpulse.exception;

public class ResourceAlreadyDisabledException extends RuntimeException{
    public ResourceAlreadyDisabledException(String message) {
        super(message);
    }

    /**
     * Exception thrown when a resource already exists in the database.
     * 
     * @param resourceName Name of entity (e.g., "Vehicle", "Driver")
     * @param fieldName Name of the duplicated field (e.g., "License Plate", "CNH")
     * @param value Value that the user tried
     */

    public ResourceAlreadyDisabledException(String resourceName, String fieldName, String value) {
        super(String.format("%s with %s '%s' is already deactivated.", resourceName, fieldName, value));
    } 
}
