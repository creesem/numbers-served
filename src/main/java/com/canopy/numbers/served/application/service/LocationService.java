package com.canopy.numbers.served.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.Location;
import com.canopy.numbers.served.application.repository.LocationRepository;

@Service
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;

	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}
}
