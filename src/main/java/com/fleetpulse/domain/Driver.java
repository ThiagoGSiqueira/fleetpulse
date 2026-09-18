package com.fleetpulse.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fleetpulse.exception.DriverCannotBeAssignedException;
import com.fleetpulse.exception.ResourceAlreadyDisabledException;
import com.fleetpulse.exception.ResourceCannotBeDeactivatedException;

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
    // Using SEQUENCE strategy to enable batching and improve performance with
    // Spring Batch/Kafka over IDENTITY
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

    public void updatePersonalData(String newName, String newCnhNumber) {
        if (newName != null) {
            this.name = newName;
        }
        if (newCnhNumber != null) {
            this.cnhNumber = newCnhNumber;
        }
    }

    public void deactivate() {
        if (this.status == DriverStatus.INACTIVE) {
            throw new ResourceAlreadyDisabledException("Driver", "CNH", this.cnhNumber);
        }

        if (this.status == DriverStatus.BUSY) {
            throw new ResourceCannotBeDeactivatedException("Driver", "CNH", this.cnhNumber);
        }

        this.status = DriverStatus.INACTIVE;
    }

    public void canBeAssignedToTrip() {
        if(this.status == DriverStatus.BUSY || this.status == DriverStatus.INACTIVE) {
            throw new DriverCannotBeAssignedException("Driver", "CNH", this.cnhNumber);
        }
    }
}
