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

public String getFormattedAddress() {
    return String.format("%s, %s, %s - %s, %s", street, neighborhood, city, state, cep);
}

public double getLatitude() {
    return Double.parseDouble(location().coordinates().latitude);
}

public double getLongitude() {
    return Double.parseDouble(location().coordinates().longitude);
}

}

