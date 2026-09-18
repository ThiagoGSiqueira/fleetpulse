package com.fleetpulse.exception;

public class TripAlreadyCancelledException extends RuntimeException{
     public TripAlreadyCancelledException(String message) {
        super(message);
    }

    public TripAlreadyCancelledException(String resourceName, String value) {
        super(String.format("%s with ID: %s is already cancelled.", resourceName, value));
    }
}
