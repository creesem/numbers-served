package com.canopy.numbers.served.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.CaresFormReason;
import com.canopy.numbers.served.application.repository.ReasonRepository;

@Service
public class ReasonService {
	@Autowired
	private ReasonRepository reasonRepository;

	public List<CaresFormReason> getAllReasons() {
		return reasonRepository.findAll();
	}

	public CaresFormReason findById(Long id) {
		return reasonRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Reason with ID " + id + " not found"));
	}
}