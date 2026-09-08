package com.fleetpulse.web.dto;

public class DriverRequestDto {
    private final String name;
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
