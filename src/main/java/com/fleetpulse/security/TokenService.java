package com.fleetpulse.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fleetpulse.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component 
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String secret;


    @Value("${api.security.jwt.expiration-ms}")
    private Long expirationMs;

    private SecretKey getSigning() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMs);

        String token = Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSigning())
                .compact();

        return token;
    }
}
