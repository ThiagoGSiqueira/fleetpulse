package com.fleetpulse.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fleetpulse.exception.BusinessRuleException;
import com.fleetpulse.exception.ConflictException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq_gen")
    @SequenceGenerator(name = "driver_seq_gen", sequenceName = "seq_driver", allocationSize = 50)
    private Long id;

    @Column(nullable = false, length = 40)
    @Size(min = 3, max = 40)
    @NotBlank
    private String name;

    @Column(unique = true, nullable = false, length = 6)
    @Size(min = 6, max = 6)
    @NotBlank
    private String cnhNumber;

    @Column(nullable = false)
    @NotNull
    private Integer tripsSinceLastDayOff = 0;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @CreatedDate
    private LocalDateTime creationDate;

    @Builder
    public Driver(String name, String cnhNumber) {
        this.name = name;
        this.cnhNumber = cnhNumber;
        this.status = DriverStatus.AVAILABLE;
    }

    // =========================================================================
    // Operational Cycle
    // =========================================================================
    public void assignToTrip() {
        if (this.status != DriverStatus.AVAILABLE) {
            throw new BusinessRuleException(
                    String.format("Driver with CNH '%s' cannot be assigned to a trip. Status: %s", this.getCnhNumber(),
                            this.getStatus()));
        }

        this.status = DriverStatus.BUSY;
    }

    public void makeAvailable() {
        this.validateIsActive();
        if (this.status == DriverStatus.AVAILABLE) {
            throw new ConflictException(String.format("Driver with CNH '%s' already available. Status: %s",
                    this.getCnhNumber(), this.getStatus()));
        }

        this.status = DriverStatus.AVAILABLE;
    }

    public void incrementTripsSinceLastDayOff() {
        this.tripsSinceLastDayOff += 1;
    }

    // =========================================================================
    // Off Duty Cycle
    // =========================================================================

    public boolean shouldGoOffDuty() {
        return this.tripsSinceLastDayOff >= 3;
    }

    public void goOffDuty() {
        this.validateIsActive();
        if (this.status == DriverStatus.OFF_DUTY) {
            throw new ConflictException(String.format("Driver with CNH '%s' already off duty. Status: %s",
                    this.getCnhNumber(), this.getStatus()));
        }

        this.tripsSinceLastDayOff = 0;
        this.status = DriverStatus.OFF_DUTY;
    }

    // =========================================================================
    // Administrative Actions
    // =========================================================================
    public void updatePersonalData(String newName, String newCnhNumber) {
        if (this.status == DriverStatus.INACTIVE) {
            throw new BusinessRuleException(
                    String.format("Driver with CNH '%s' cannot be modified. Status: %s", this.getCnhNumber(),
                            this.getStatus()));
        }
        if (newName != null) {
            this.name = newName;
        }
        if (newCnhNumber != null) {
            this.cnhNumber = newCnhNumber;
        }
    }

    public void deactivate() {
        this.validateIsActive();
        if (this.status == DriverStatus.BUSY) {
            throw new BusinessRuleException(
                    String.format("Driver with CNH '%s' cannot be deactivated. Status: %s", this.getCnhNumber(),
                            this.getStatus()));
        }

        this.status = DriverStatus.INACTIVE;
    }

    // =========================================================================
    // Private Validations
    // =========================================================================
    private void validateIsActive() {
        if (this.status == DriverStatus.INACTIVE) {
            throw new BusinessRuleException(String.format("Driver with CNH '%s' is inactive. Status: %s",
                    this.getCnhNumber(), this.getStatus()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Driver driver = (Driver) o;
        return Objects.equals(this.getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
