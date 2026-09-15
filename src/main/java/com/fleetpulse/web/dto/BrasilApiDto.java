package com.fleetpulse.web.dto;

public record BrasilApiDto(
    String cep,
    String state,
    String city,
    String neighborhood,
    String street,
    Location location
) {
    
public record Location(
    Coordinates coordinates
) {}

public record Coordinates(
    String longitude,
    String latitude
) {}

}

