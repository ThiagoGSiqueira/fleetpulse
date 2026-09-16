package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.exception.ResourceAlreadyExistsException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.mapper.DriverMapper;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.web.dto.DriverRequestDto;
import com.fleetpulse.web.dto.DriverRequestUpdateDto;
import com.fleetpulse.web.dto.DriverResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    // Create - Read - Update - Delete
    @Transactional
    public DriverResponseDto createDriver(DriverRequestDto driverDto) {
        Driver driverEntity = driverMapper.toEntity(driverDto);
        if (driverRepository.existsByCnhNumber(driverDto.cnhNumber())) {
            throw new ResourceAlreadyExistsException("Driver", "CNH", driverDto.cnhNumber());
        }
        driverRepository.save(driverEntity);
        return driverMapper.toDto(driverEntity);
    }

    @Transactional(readOnly = true)
    public List<DriverResponseDto> findAllDrivers() {
        return driverRepository.findAll().stream()
                .map(driverEntity -> driverMapper.toDto(driverEntity))
                .toList();
    }

    @Transactional(readOnly = true)
    public DriverResponseDto findDriverById(Long id) {
        Driver driverEntity = findDriverEntityById(id);
        return driverMapper.toDto(driverEntity);
    }

    @Transactional
    public DriverResponseDto updateDriver(Long id, DriverRequestUpdateDto driverDto) {
        Driver driverEntity = findDriverEntityById(id);
        if (driverDto.name() != null) {
            driverEntity.setName(driverDto.name());
        }
        if (driverDto.cnhNumber() != null) {
            driverEntity.setCnhNumber(driverDto.cnhNumber());
        }
        driverRepository.save(driverEntity);
        return driverMapper.toDto(driverEntity);
    }

    @Transactional
    public void deleteDriver(Long id) {
        Driver driverEntity = findDriverEntityById(id);
        driverRepository.delete(driverEntity);
    }

    @Transactional(readOnly = true)
    public Driver findDriverEntityById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", id.toString()));
    }
}
