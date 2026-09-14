package com.fleetpulse.config;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fleetpulse.web.dto.BrasilApiDto;
@Component 
public class BrasilApiClient {
    private final RestClient restClient;

    public BrasilApiClient(RestClient.Builder builder) {
        this.restClient = builder
        .baseUrl("https://brasilapi.com.br/api/cep/v2")
        .build();
    }

    public BrasilApiDto searchAddress(String cep) {
        return restClient.get()
        .uri("/{cep}", cep)
        .retrieve()
        .body(BrasilApiDto.class);
    }
}
