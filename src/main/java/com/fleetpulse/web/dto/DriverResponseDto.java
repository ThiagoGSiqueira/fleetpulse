package com.fleetpulse.web.dto;

import com.fleetpulse.entity.DriverStatus;

public class DriverResponseDto {
    private final Long id;
    private final String name;
    private final String cnhNumber;
    private final DriverStatus status;

    public DriverResponseDto(Long id, String name, String cnhNumber, DriverStatus status) {
        this.id = id;
        this.name = name;
        this.cnhNumber = cnhNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
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
