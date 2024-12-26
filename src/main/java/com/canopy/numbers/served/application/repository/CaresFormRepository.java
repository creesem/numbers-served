package com.canopy.numbers.served.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.data.CaresFormReason;

@Repository
public interface CaresFormRepository extends JpaRepository<CaresForm, Long> {
	List<CaresForm> findByLocation(CaresFormLocation location);

	List<CaresForm> findByReasonForVisit(CaresFormReason reason);
}