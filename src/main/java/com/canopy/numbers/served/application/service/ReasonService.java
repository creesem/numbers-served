package com.canopy.numbers.served.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.Reason;
import com.canopy.numbers.served.application.repository.ReasonRepository;

@Service
public class ReasonService {
	@Autowired
	private ReasonRepository reasonRepository;

	public List<Reason> getAllReasons() {
		return reasonRepository.findAll();
	}
}