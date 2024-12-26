package com.canopy.numbers.served.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.CanopySchoolForm;
import com.canopy.numbers.served.application.repository.CanopySchoolFormRepository;

@Service
public class CanopySchoolFormService {

	private final CanopySchoolFormRepository canopySchoolFormRepository;

	@Autowired
	public CanopySchoolFormService(CanopySchoolFormRepository canopySchoolFormRepository) {
		this.canopySchoolFormRepository = canopySchoolFormRepository;
	}

	public CanopySchoolForm save(CanopySchoolForm canopySchoolForm) {
		return canopySchoolFormRepository.save(canopySchoolForm);
	}

}
