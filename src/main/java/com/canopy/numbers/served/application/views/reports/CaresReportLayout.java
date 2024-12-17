package com.canopy.numbers.served.application.views.reports;

import com.canopy.numbers.served.application.data.CaresForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CaresReportLayout {

	public Div createLayout() {
		Div layout = new Div();
		layout.setSizeFull();

		// Filters section
		HorizontalLayout filtersLayout = new HorizontalLayout();
		filtersLayout.setWidthFull();

		ComboBox<String> locationFilter = new ComboBox<>("Location");
		locationFilter.setItems("PRTF", "CARES School Jackson", "CARES School Biloxi", "CARES School Hattiesburg");
		locationFilter.setPlaceholder("Select Location");

		ComboBox<String> reasonFilter = new ComboBox<>("Reason for Visit");
		reasonFilter.setItems("IEP Meeting", "Parent Teacher Conference", "Principal Meeting", "Counselor Meeting",
				"Supply Dropoff", "Other");
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
		caresGrid.setColumns("visitorName", "associatedStudent", "dateTime", "location", "reason");

		// Add to layout
		layout.add(filtersLayout, caresGrid);
		layout.getStyle().set("background-color", "#f9f9f9");

		return layout;
	}

}