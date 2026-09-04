package com.fleetpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleetpulse.entity.Driver;

@Repository
// Entidade que o Spring vai manipular (Driver, mapeada pra tabela "drivers")
// e tipo da PK dela (Long)
public interface DriverRepository extends JpaRepository<Driver, Long>{

}
