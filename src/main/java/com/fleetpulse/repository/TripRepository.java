package com.fleetpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetpulse.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long>{

}
