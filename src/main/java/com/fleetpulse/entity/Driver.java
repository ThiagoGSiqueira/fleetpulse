package com.fleetpulse.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "drivers")
public class Driver {
    // Using SEQUENCE strategy to enable batching and improve performance with Spring Batch/Kafka over IDENTITY
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq_gen")
    @SequenceGenerator(
        name = "driver_seq_gen",
        sequenceName = "seq_driver",
        allocationSize = 50
    )
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String cnhNumber;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.AVAILABLE;

    @CreatedDate 
    private LocalDateTime creationDate;    
}
