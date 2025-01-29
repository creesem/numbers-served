package com.canopy.numbers.served.application.data;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class TcsForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String guardianName;

	private LocalDateTime dateTimeOfVisit;

	private String studentFullname;

	private String location;

	// Default constructor
	public TcsForm() {
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public LocalDateTime getDateTimeOfVisit() {
		return dateTimeOfVisit;
	}

	public void setDateTimeOfVisit(LocalDateTime dateTimeOfVisit) {
		this.dateTimeOfVisit = dateTimeOfVisit;
	}

	public String getStudentFullname() {
		return studentFullname;
	}

	public void setStudentFullname(String studentFullname) {
		this.studentFullname = studentFullname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
