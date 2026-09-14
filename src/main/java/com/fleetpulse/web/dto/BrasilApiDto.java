package com.fleetpulse.web.dto;

public record BrasilApiDto(
    String cep,
    String state,
    String city,
    String neighborhood,
    String street,
    Location location
) {
    
}

record Location(
    Coordinates coordinates
) {}

record Coordinates(
    String longitude,
    String latitude
) {}