package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.entity.Driver;
import com.fleetpulse.entity.DriverStatus;
import com.fleetpulse.mapper.DriverMapper;
import com.fleetpulse.repository.DriverRepository;
import com.fleetpulse.web.dto.DriverRequestDto;
import com.fleetpulse.web.dto.DriverRequestUpdateDto;
import com.fleetpulse.web.dto.DriverResponseDto;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    // Create - Read - Update - Delete
    @Transactional
    public DriverResponseDto createDriver(DriverRequestDto driverDto) {
        Driver driverEntity =  driverMapper.toEntity(driverDto);
        driverEntity.setStatus(DriverStatus.AVAILABLE);
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
        Driver driverEntity = driverRepository.findById(id).orElseThrow();
        return driverMapper.toDto(driverEntity);
    }

    @Transactional
    public DriverResponseDto updateDriver(Long id, DriverRequestUpdateDto driverDto) {
        Driver driverEntity = driverRepository.findById(id).orElseThrow();
        if (driverDto.getName() != null) {
            driverEntity.setName(driverDto.getName());
        }
        if (driverDto.getCnhNumber() != null) {
            driverEntity.setCnhNumber(driverDto.getCnhNumber());
        }

        return driverMapper.toDto(driverEntity);
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}
