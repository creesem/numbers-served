package com.canopy.numbers.served.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.NumbersServedStudent;
import com.canopy.numbers.served.application.repository.NumbersServedStudentRepository;

@Service
public class NumbersServedStudentService {

	@Autowired
	private NumbersServedStudentRepository studentRepository;

	public void saveAll(List<NumbersServedStudent> students) {
		studentRepository.saveAll(students);
	}
}
