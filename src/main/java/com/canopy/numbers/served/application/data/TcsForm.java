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

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = true)
    private NumbersServedStudent associatedStudent;

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

    public NumbersServedStudent getAssociatedStudent() {
        return associatedStudent;
    }

    public void setAssociatedStudent(NumbersServedStudent associatedStudent) {
        this.associatedStudent = associatedStudent;
    }
}
