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

	@ManyToOne
	@JoinColumn(name = "reason_id", nullable = false)
	private CaresFormReason reasonForVisit;

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id", nullable = true)
	private NumbersServedStudent associatedStudent;

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

	public NumbersServedStudent getAssociatedStudent() {
		return associatedStudent;
	}

	public void setAssociatedStudent(NumbersServedStudent associatedStudent) {
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

	public String getStudentFullname() {
		return studentFullname;
	}

	public void setStudentFullname(String studentFullname) {
		this.studentFullname = studentFullname;
	}

}
