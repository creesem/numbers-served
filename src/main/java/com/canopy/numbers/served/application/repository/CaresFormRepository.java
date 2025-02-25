package com.canopy.numbers.served.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.data.CaresFormReason;
import com.canopy.numbers.served.application.data.NumbersServedStudent;

@Repository
public interface CaresFormRepository extends JpaRepository<CaresForm, Long> {
	List<CaresForm> findByLocation(CaresFormLocation location);

	List<CaresForm> findByReasonForVisit(CaresFormReason reason);

	Optional<CaresForm> findByStudentFullname(String studentFullname);
}