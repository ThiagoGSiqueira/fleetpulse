package com.fleetpulse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fleetpulse.web.dto.BrasilApiDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class BrasilApiService {

    private final RestClient brasilApiClient;

     public BrasilApiDto searchAddress(String cep) {
        return brasilApiClient.get()
        .uri("/{cep}", cep)
        .retrieve()
        .body(BrasilApiDto.class);
    }
}
