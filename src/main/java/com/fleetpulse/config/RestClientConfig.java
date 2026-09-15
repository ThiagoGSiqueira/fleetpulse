package com.fleetpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    
    @Bean 
    public RestClient brasilApiClient() {
        return RestClient.builder()
        .baseUrl("https://brasilapi.com.br/api/cep/v2")
        .build();
    }
     
}
