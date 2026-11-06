package com.fleetpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetpulse.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
