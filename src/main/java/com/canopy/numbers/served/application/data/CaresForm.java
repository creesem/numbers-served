package com.canopy.numbers.served.application.data;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CaresForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String visitorName;
	private String associatedStudent;
	private LocalDateTime dateTimeOfVisit;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private CaresFormLocation location;

	@ManyToOne
	@JoinColumn(name = "reason_id", nullable = false)
	private CaresFormReason reasonForVisit;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getAssociatedStudent() {
		return associatedStudent;
	}

	public void setAssociatedStudent(String associatedStudent) {
		this.associatedStudent = associatedStudent;
	}

	public LocalDateTime getDateTimeOfVisit() {
		return dateTimeOfVisit;
	}

	public void setDateTimeOfVisit(LocalDateTime dateTimeOfVisit) {
		this.dateTimeOfVisit = dateTimeOfVisit;
	}

	public CaresFormLocation getLocation() {
		return location;
	}

	public void setLocation(CaresFormLocation location) {
		this.location = location;
	}

	public CaresFormReason getReasonForVisit() {
		return reasonForVisit;
	}

	public void setReasonForVisit(CaresFormReason reasonForVisit) {
		this.reasonForVisit = reasonForVisit;
	}
}
