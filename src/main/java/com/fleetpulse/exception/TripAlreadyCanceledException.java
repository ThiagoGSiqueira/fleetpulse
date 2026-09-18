package com.fleetpulse.exception;

public class TripAlreadyCanceledException extends RuntimeException{
     public TripAlreadyCanceledException(String message) {
        super(message);
    }

    public TripAlreadyCanceledException(String resourceName, String value) {
        super(String.format("%s with ID: %s is already canceled.", resourceName, value));
    }
}
