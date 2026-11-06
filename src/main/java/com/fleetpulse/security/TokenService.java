package com.fleetpulse.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service 
public class TokenService {

    @Value("${api.security.jwt.secret}")
    String secret;

    @Value("${api.security.jwt.expiration-ms")
    String expiration;
}
