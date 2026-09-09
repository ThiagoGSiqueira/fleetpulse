package com.fleetpulse.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DriverRequestDto {
    @NotBlank 
    @Size(min = 3, max = 40)
    private final String name;
    @NotBlank 
    @Size(min = 6, max = 6, message = "size must be 6 characters long")
    private final String cnhNumber;

    public DriverRequestDto(String name, String cnhNumber) {
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
