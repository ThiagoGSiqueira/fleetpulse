package com.fleetpulse.exception;

public class TripAlreadyCancelledException extends RuntimeException{
     public TripAlreadyCancelledException(String message) {
        super(message);
    }

    /**
     * Exception thrown when a resource not found in the database
     * 
     * @param resourceName Name of entity (e.g., "Vehicle", "Driver")
     * @param value Value that the user tried
     */

    public TripAlreadyCancelledException(String resourceName, String value) {
        super(String.format("%s with ID: %s is already cancelled.", resourceName, value));
    }
}
