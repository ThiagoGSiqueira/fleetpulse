package com.fleetpulse.exception;

public class CnhAlreadyExistsException extends RuntimeException{
    public CnhAlreadyExistsException(String cnhNumber) {
        super("A driver with CNH " + cnhNumber + " already exists");
    }
}
