package com.canopy.numbers.served.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.repository.LocationRepository;

@Service
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;

	public List<CaresFormLocation> getAllLocations() {
		return locationRepository.findAll();
	}

	public CaresFormLocation findById(Long id) {
		return locationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Location with ID " + id + " not found"));
	}
}
