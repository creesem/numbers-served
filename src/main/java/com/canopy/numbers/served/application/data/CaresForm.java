package com.canopy.numbers.served.application.data;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CaresForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String visitorName;
	private String associatedStudent;
	private LocalDateTime dateTimeOfVisit;
	private String prtfOrCaresSchool;
	private String reasonForVisit;

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

	public String getPrtfOrCaresSchool() {
		return prtfOrCaresSchool;
	}

	public void setPrtfOrCaresSchool(String prtfOrCaresSchool) {
		this.prtfOrCaresSchool = prtfOrCaresSchool;
	}

	public String getReasonForVisit() {
		return reasonForVisit;
	}

	public void setReasonForVisit(String reasonForVisit) {
		this.reasonForVisit = reasonForVisit;
	}
}