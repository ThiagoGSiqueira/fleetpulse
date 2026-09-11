package com.fleetpulse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetpulse.domain.Driver;
import com.fleetpulse.domain.DriverStatus;
import com.fleetpulse.exception.CnhAlreadyExistsException;
import com.fleetpulse.exception.DriverNotFoundException;
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
        Driver driverEntity =  driverMapper.toEntity(driverDto);
        if (driverRepository.existsByCnhNumber(driverDto.cnhNumber())) {
            throw new CnhAlreadyExistsException(driverDto.cnhNumber());
        }
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
        Driver driverEntity = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(id));
        return driverMapper.toDto(driverEntity);
    }

    @Transactional
    public DriverResponseDto updateDriver(Long id, DriverRequestUpdateDto driverDto) {
        Driver driverEntity = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(id));
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
        if (!driverRepository.existsById(id)) {
            throw new DriverNotFoundException(id);
        }
        driverRepository.deleteById(id);
    }
}
