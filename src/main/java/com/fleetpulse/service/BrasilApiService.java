package com.fleetpulse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fleetpulse.web.dto.BrasilApiDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class BrasilApiService {

    private final RestClient brasilApiClient;

     public BrasilApiDTO searchAddress(String cep) {
        return brasilApiClient.get()
        .uri("/{cep}", cep)
        .retrieve()
        .body(BrasilApiDTO.class);
    }
}
