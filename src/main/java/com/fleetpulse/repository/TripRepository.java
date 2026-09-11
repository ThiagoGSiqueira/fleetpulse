package com.fleetpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetpulse.domain.Trip;

public interface TripRepository extends JpaRepository<Trip, Long>{

}
