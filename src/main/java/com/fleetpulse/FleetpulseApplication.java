package com.fleetpulse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fleetpulse.entity.Driver;
import com.fleetpulse.entity.DriverStatus;
import com.fleetpulse.entity.Trip;
import com.fleetpulse.entity.TripStatus;
import com.fleetpulse.entity.Vehicle;
import com.fleetpulse.entity.VehicleStatus;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.repository.TripRepository;
import com.fleetpulse.repository.VehicleRepository;

@SpringBootApplication
public class FleetpulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FleetpulseApplication.class, args);
	}

}
