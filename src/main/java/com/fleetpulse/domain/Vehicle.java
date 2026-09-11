package com.fleetpulse.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {
    // Using SEQUENCE strategy to enable batching and improve performance with Spring Batch/Kafka over IDENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq_gen")
    @SequenceGenerator (
        name = "vehicle_seq_gen",
        sequenceName = "seq_vehicle",
        allocationSize = 50
    )
    private Long id;

    @Column(unique = true, nullable = false, length = 7)
    @NotBlank
    private String licensePlate;

    @Column(nullable = false, length = 40)
    @NotBlank 
    private String model;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.AVAILABLE;
}
