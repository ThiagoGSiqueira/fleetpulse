package com.fleetpulse.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "trips")
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreatedDate 
    private LocalDateTime createdAt;

    @LastModifiedDate 
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotBlank 
    @Column(nullable = false, length = 10)
    private String originZipCode;

    @NotBlank 
    @Column(nullable = false, length = 120)
    private String originAddress;

    @NotNull 
    @Column(nullable = false)
    private Double originLatitude;

    @NotNull 
    @Column(nullable = false)
    private Double originLongitude;

    @NotBlank 
    @Column(nullable = false, length = 10)
    private String destinationZipCode;

    @NotBlank 
    @Column(nullable = false, length = 120)
    private String destinationAddress;

    @NotNull 
    @Column(nullable = false)
    private Double destinationLatitude;

    @NotNull 
    @Column(nullable = false)
    private Double  destinationLongitude;

    @NotNull 
    @Column(nullable = false)
    private Double distanceInKm;

    @NotNull  
    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TripStatus status;
}
