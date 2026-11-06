package com.fleetpulse.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 40)
    @Size(min = 3, max = 40)
    @NotBlank
    String name;

    @Column(nullable = false, length = 200)
    @NotBlank 
    String email;

    @Column(nullable = false, length = 150)
    @NotBlank 
    String password;

    @NotNull 
    UserRole role;
}
