package com.canopy.numbers.served.application.data;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CaresFormReason {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	public CaresFormReason() {
	}

	public CaresFormReason(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CaresFormReason that = (CaresFormReason) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}