package com.fleetpulse.web.dto;

import com.fleetpulse.entity.DriverStatus;

public class DriverRequestDto {
    private final String name;
    private final String cnhNumber;
    private final DriverStatus status;

    public DriverRequestDto(String name, String cnhNumber, DriverStatus status) {
        this.name = name;
        this.cnhNumber = cnhNumber;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getCnhNumber() {
        return cnhNumber;
    }

    public DriverStatus getStatus() {
        return status;
    }
}
