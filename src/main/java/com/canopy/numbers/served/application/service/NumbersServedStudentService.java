package com.canopy.numbers.served.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.NumbersServedStudent;
import com.canopy.numbers.served.application.repository.NumbersServedStudentRepository;

@Service
public class NumbersServedStudentService {

	@Autowired
	private NumbersServedStudentRepository repository;

	public void saveAll(List<NumbersServedStudent> students) {
		repository.saveAll(students);
	}

	public boolean existsByStudentIdAndNameAndBirthDate(int studentId, String lastName, String firstName,
			LocalDate birthDate) {
		return repository.existsByStudentIdAndLastNameAndFirstNameAndBirthDate(studentId, lastName, firstName,
				birthDate);
	}

	public NumbersServedStudent findByStudentIdAndNameAndBirthDate(int studentId, String lastName, String firstName,
			LocalDate birthDate) {
		return repository.findByStudentIdAndLastNameAndFirstNameAndBirthDate(studentId, lastName, firstName, birthDate);
	}

	public NumbersServedStudent save(NumbersServedStudent student) {
		return repository.save(student);
	}

	public List<NumbersServedStudent> findAll() {
		return repository.findAll();
	}

}
