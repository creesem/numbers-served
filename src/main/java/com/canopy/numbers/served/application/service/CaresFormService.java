package com.canopy.numbers.served.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.data.CaresFormReason;
import com.canopy.numbers.served.application.data.NumbersServedStudent;
import com.canopy.numbers.served.application.repository.CaresFormRepository;

@Service
public class CaresFormService {
	@Autowired
	private CaresFormRepository caresFormRepository;

	@Autowired
	private CaresFormLocationService locationService;

	@Autowired
	private CaresFormReasonService reasonService;

	public CaresForm createCaresForm(String visitorName, NumbersServedStudent associatedStudent,
			LocalDateTime dateTimeOfVisit, Long locationId, Long reasonId) {
		CaresForm form = new CaresForm();
		form.setVisitorName(visitorName);
		form.setAssociatedStudent(associatedStudent);
		form.setDateTimeOfVisit(dateTimeOfVisit);

		CaresFormLocation location = locationService.findById(locationId);
		CaresFormReason reason = reasonService.findById(reasonId);

		form.setLocation(location);
		form.setReasonForVisit(reason);

		return caresFormRepository.save(form);
	}

	public CaresForm save(CaresForm caresForm) {
		return caresFormRepository.save(caresForm);
	}

	public CaresForm findByAssociatedStudent(NumbersServedStudent associatedStudent) {
		Optional<CaresForm> caresForm = caresFormRepository.findByAssociatedStudent(associatedStudent);
		return caresForm.orElse(null);
	}

	public List<CaresForm> findAll() {
		return caresFormRepository.findAll();
	}

}