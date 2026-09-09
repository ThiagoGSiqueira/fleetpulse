package com.fleetpulse.exception;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException(Long id) {
        super("Driver with ID: " + id + " not found.");
    }
}
