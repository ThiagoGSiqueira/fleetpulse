package com.fleetpulse.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.User;
import com.fleetpulse.domain.UserRole;
import com.fleetpulse.exception.ConflictException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.DriverMapper;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.repository.UserRepository;
import com.fleetpulse.web.dto.DriverRequestDTO;
import com.fleetpulse.web.dto.DriverRequestUpdateDTO;
import com.fleetpulse.web.dto.DriverResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Create - Read - Update - Delete
    @Transactional
    public DriverResponseDTO create(DriverRequestDTO request) {
        if (driverRepository.existsByCnhNumber(request.cnhNumber())) {
            throw new ConflictException("Driver with CNH already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use.");
        }

        // 1. cria o usuário com role DRIVER
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // salva só o hash
                .role(UserRole.ROLE_DRIVER)
                .build();
        userRepository.save(user);

        // 2. cria o driver apontando para ele
        Driver driver = Driver.builder().user(user).cnhNumber(request.cnhNumber()).build();
        driverRepository.save(driver);

        return driverMapper.toDto(driver);
    }

    @Transactional(readOnly = true)
    public List<DriverResponseDTO> findAll() {
        return driverRepository.findAll().stream()
                .map(driverMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DriverResponseDTO findById(Long id) {
        Driver driver = findEntityById(id);

        return driverMapper.toDto(driver);
    }

    @Transactional
    public DriverResponseDTO resumeDuty(Long id) {
        Driver driver = findEntityById(id);

        driver.resumeDuty();

        return driverMapper.toDto(driver);
    }

    @Transactional
    public DriverResponseDTO update(Long id, DriverRequestUpdateDTO request) {
        Driver driver = findEntityById(id);

        driver.updatePersonalData(request.cnhNumber());

        return driverMapper.toDto(driver);
    }

    @Transactional
    public void delete(Long id) {
        Driver driver = findEntityById(id);
        driver.deactivate();
    }

    @Transactional(readOnly = true)
    public Driver findEntityById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Driver with ID: %s not found", id.toString())));
    }

}
