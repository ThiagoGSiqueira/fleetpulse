package com.fleetpulse.web.dto;

import jakarta.validation.constraints.Size;

public record DriverRequestUpdateDTO(
    @Size(min = 3, max = 40)
    String name,
    @Size(min = 6, max = 6, message = "size must be 6 characters long")
    String cnhNumber
) {

}
