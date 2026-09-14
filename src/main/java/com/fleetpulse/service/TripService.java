package com.fleetpulse.service;

import org.springframework.stereotype.Service;

import com.fleetpulse.mapper.TripMapper;
import com.fleetpulse.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
}
