package com.fleetpulse.web.dto;

import jakarta.validation.constraints.Size;

public record TripRequestUpdateDTO(
    @Size(min = 8, max = 10)
    String originZipCode,
    @Size(min = 8, max = 10)
    String destinationZipCode
) {

}
