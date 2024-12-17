package com.canopy.numbers.served.application.views.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.Location;
import com.canopy.numbers.served.application.data.Reason;
import com.canopy.numbers.served.application.service.LocationService;
import com.canopy.numbers.served.application.service.ReasonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CaresReportLayout {

	@Autowired
	private LocationService locationService;

	@Autowired
	private ReasonService reasonService;

	public Div createLayout() {
		Div layout = new Div();
		layout.setSizeFull();

		// Filters section
		HorizontalLayout filtersLayout = new HorizontalLayout();
		filtersLayout.setWidthFull();

		ComboBox<String> locationFilter = new ComboBox<>("Location");
		List<Location> locations = locationService.getAllLocations();
		List<String> locationNames = locations.stream().map(Location::getName).toList();
		locationFilter.setItems(locationNames);
		locationFilter.setPlaceholder("Select Location");

		ComboBox<String> reasonFilter = new ComboBox<>("Reason for Visit");
		List<Reason> reasons = reasonService.getAllReasons();
		List<String> reasonNames = reasons.stream().map(Reason::getName).toList();
		reasonFilter.setItems(reasonNames);
		reasonFilter.setPlaceholder("Select Reason");

		DatePicker startDate = new DatePicker("Start Date");
		DatePicker endDate = new DatePicker("End Date");

		Button applyFiltersButton = new Button("Apply Filters");
		Button clearFiltersButton = new Button("Clear Filters", e -> {
			locationFilter.clear();
			reasonFilter.clear();
			startDate.clear();
			endDate.clear();
		});

		filtersLayout.add(locationFilter, reasonFilter, startDate, endDate, applyFiltersButton, clearFiltersButton);

		// Table to display report data
		Grid<CaresForm> caresGrid = new Grid<>(CaresForm.class);
		caresGrid.setSizeFull();
		caresGrid.setColumns("visitorName", "associatedStudent", "dateTimeOfVisit", "prtfOrCaresSchool",
				"reasonForVisit");

		// Add to layout
		layout.add(filtersLayout, caresGrid);
		layout.getStyle().set("background-color", "#f9f9f9");

		return layout;
	}

}