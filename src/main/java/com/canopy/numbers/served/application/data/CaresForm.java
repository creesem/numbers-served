package com.canopy.numbers.served.application.data;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class CaresForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String visitorName;
	private LocalDateTime dateTimeOfVisit;
	private String studentFullname;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private CaresFormLocation location;

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

	public String getReasonForVisit() {
		return reasonForVisit;
	}

	public void setReasonForVisit(String reasonForVisit) {
		this.reasonForVisit = reasonForVisit;
	}

	public String getStudentFullname() {
		return studentFullname;
	}

	public void setStudentFullname(String studentFullname) {
		this.studentFullname = studentFullname;
	}

}
