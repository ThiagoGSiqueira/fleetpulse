package com.fleetpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleetpulse.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>{
    boolean existsByCnhNumber(String cnhNumber);
}
