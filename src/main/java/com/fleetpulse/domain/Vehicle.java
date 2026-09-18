package com.fleetpulse.domain;

import com.fleetpulse.exception.BusinessRuleException;
import com.fleetpulse.exception.ConflictException;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "vehicles")
public class Vehicle {
    // Using SEQUENCE strategy to enable batching and improve performance with
    // Spring Batch/Kafka over IDENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq_gen")
    @SequenceGenerator(name = "vehicle_seq_gen", sequenceName = "seq_vehicle", allocationSize = 50)
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

    @Builder
    public Vehicle(String licensePlate, String model) {
        this.licensePlate = licensePlate;
        this.model = model;
    }

    public void updateVehicleData(String newLicensePlate, String newModel) {
        if (this.status == VehicleStatus.INACTIVE) {
            throw new BusinessRuleException(
                    String.format("Vehicle with License Plate '%s' cannot be modified", this.getLicensePlate()));
        }
        if (newLicensePlate != null) {
            this.licensePlate = newLicensePlate;
        }
        if (newModel != null) {
            this.model = newModel;
        }
    }

    public void deactivate() {
        if (this.status == VehicleStatus.INACTIVE) {
            throw new ConflictException(
                    String.format("Vehicle with License Plate '%s' is already deactivated", this.getLicensePlate()));
        }
        this.status = VehicleStatus.INACTIVE;
    }

    public void assignToTrip() {
        if (this.status == VehicleStatus.INACTIVE || this.status == VehicleStatus.MAINTENANCE
                || this.status == VehicleStatus.BUSY) {
            throw new BusinessRuleException(String
                    .format("Vehicle with License Plate '%s' cannot be assigned to a trip", this.getLicensePlate()));
        }

        this.status = VehicleStatus.BUSY;
    }

    public void sendToMaintenance() {
        if (this.status == VehicleStatus.INACTIVE || this.status == VehicleStatus.BUSY || this.status == VehicleStatus.MAINTENANCE) {
            throw new BusinessRuleException(String.format("Vehicle with License Plate '%s' cannot be sent to maintenance.", this.getLicensePlate()));
        }

        this.status = VehicleStatus.MAINTENANCE;
    }
}
