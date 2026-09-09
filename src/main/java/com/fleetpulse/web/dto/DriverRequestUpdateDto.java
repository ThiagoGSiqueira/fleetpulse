package com.fleetpulse.web.dto;

import jakarta.validation.constraints.Size;

public class DriverRequestUpdateDto {

    @Size(min = 3, max = 40)
    private final String name; 
    @Size(min = 6, max = 6, message = "size must be 6 characters long")
    private final String cnhNumber;

    public DriverRequestUpdateDto(String name, String cnhNumber) {
        this.name = name;
        this.cnhNumber = cnhNumber;
    }

    public String getName() {
        return name;
    }

    public String getCnhNumber() {
        return cnhNumber;
    }

}


