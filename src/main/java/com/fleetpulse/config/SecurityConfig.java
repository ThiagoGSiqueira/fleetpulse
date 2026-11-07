package com.fleetpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fleetpulse.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity 
@RequiredArgsConstructor 
@Configuration 
public class SecurityConfig {

private final JwtAuthenticationFilter jwtAuthenticationFilter;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) {

    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .csrf(csrf -> csrf.disable())
    .formLogin(formLogin -> formLogin.disable())
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/users/register", "/users/login").permitAll()
        .anyRequest().authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

}
