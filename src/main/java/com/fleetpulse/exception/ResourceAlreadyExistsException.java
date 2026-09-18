package com.fleetpulse.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String value) {
        super(String.format("%s with %s '%s' already exists.", resourceName, fieldName, value));
    } 
}
