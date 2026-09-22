package com.fleetpulse.web.dto;

public record AddressData(
    String zipCode,
    String address,
    Double latitude,
    Double longitude
) {
    public AddressData(BrasilApiDTO dto) {
        this(dto.cep(), dto.getFormattedAddress(), dto.getLatitude(), dto.getLongitude());
    }
}
