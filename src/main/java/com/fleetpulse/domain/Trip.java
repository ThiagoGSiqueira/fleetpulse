package com.fleetpulse.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fleetpulse.exception.BusinessRuleException;
import com.fleetpulse.exception.ConflictException;
import com.fleetpulse.util.GeoUtils;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private Double destinationLongitude;

    @NotNull
    @Column(nullable = false)
    private Double distanceInKm;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Builder
    public Trip(
            Driver driver,
            Vehicle vehicle,
            String originZipCode,
            String originAddress,
            Double originLatitude,
            Double originLongitude,
            String destinationZipCode,
            String destinationAddress,
            Double destinationLatitude,
            Double destinationLongitude) {
        this.driver = driver;
        this.vehicle = vehicle;
        this.originZipCode = originZipCode;
        this.originAddress = originAddress;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationZipCode = destinationZipCode;
        this.destinationAddress = destinationAddress;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.distanceInKm = GeoUtils.calculateEstimatedRoadDistanceInKm(this.originLatitude, this.originLongitude,
                this.destinationLatitude, this.destinationLongitude);
        this.status = TripStatus.PENDING;
    }

    public void reassignDriver(Driver newDriver) {
        if(this.driver == newDriver) {
            throw new BusinessRuleException("Driver is already assigned to this trip.");
        }
        if(newDriver.getStatus() != DriverStatus.AVAILABLE) {
            throw new BusinessRuleException("Driver is not available.");
        }
        newDriver.assignToTrip();
        this.driver.makeAvailable();
        this.driver = newDriver;
    }

    public void cancel() {
        if(this.status == TripStatus.CANCELED) {
            throw new ConflictException(String.format("Trip with ID: %s is already canceled", this.getId().toString()));
        }
        this.status = TripStatus.CANCELED;
    }

}
