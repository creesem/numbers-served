package com.canopy.numbers.served.application.views.reports;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.NumbersServedStudent;
import com.canopy.numbers.served.application.data.TcsForm;
import com.canopy.numbers.served.application.service.NumbersServedStudentService;
import com.canopy.numbers.served.application.service.TcsFormService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.xdev.vaadin.grid_exporter.GridExporter;

@Component
public class TcsReportLayout extends DownloadGridView<CaresForm> {
	private static final long serialVersionUID = 3234887931270070148L;
	private final TcsFormService tcsFormService;
	private final NumbersServedStudentService studentService;
	private Grid<TcsForm> tcsGrid;
	private List<TcsForm> currentItems;

	public Div createLayout() {
		Div layout = new Div();
		layout.setSizeFull();

		HorizontalLayout filtersLayout = createFilterLayout();

		initializeLayout();

		layout.add(filtersLayout, tcsGrid);
		layout.getStyle().set("background-color", "#f9f9f9");

		return layout;

	}

	@Autowired
	public TcsReportLayout(TcsFormService tcsFormService, NumbersServedStudentService studentService) {
		this.tcsFormService = tcsFormService;
		this.studentService = studentService;
		initializeLayout();
	}

	private void initializeLayout() {
		setSizeFull();
		HorizontalLayout filtersLayout = createFilterLayout();
		tcsGrid = new Grid<>(TcsForm.class);
		configureGrid();
		add(filtersLayout, tcsGrid);
		fetchAndDisplayData();
	}

	public HorizontalLayout createFilterLayout() {
		HorizontalLayout filtersLayout = new HorizontalLayout();
		filtersLayout.setWidthFull();

		// Associated Student Filter
		ComboBox<String> studentFilter = new ComboBox<>("Associated Student");
		List<NumbersServedStudent> students = studentService.findAll();
		List<String> studentNames = students.stream()
				.map(student -> student.getLastName() + ", " + student.getFirstName()).collect(Collectors.toList());
		studentFilter.setItems(studentNames);
		studentFilter.setPlaceholder("Select Student");

		// Date Range Filters
		DatePicker startDate = new DatePicker("Start Date");
		DatePicker endDate = new DatePicker("End Date");

		// Apply Filters Button
		Button applyFiltersButton = new Button("Apply Filters", event -> {
			String selectedStudent = studentFilter.getValue();
			LocalDate start = startDate.getValue();
			LocalDate end = endDate.getValue();

			currentItems = tcsFormService.findAll().stream().filter(form -> (selectedStudent == null || (form
					.getAssociatedStudent() != null
					&& (form.getAssociatedStudent().getLastName() + ", " + form.getAssociatedStudent().getFirstName())
							.equals(selectedStudent))))
					.filter(form -> (start == null || (form.getDateTimeOfVisit() != null
							&& !form.getDateTimeOfVisit().toLocalDate().isBefore(start))))
					.filter(form -> (end == null || (form.getDateTimeOfVisit() != null
							&& !form.getDateTimeOfVisit().toLocalDate().isAfter(end))))
					.collect(Collectors.toList());

			tcsGrid.setItems(currentItems);

			if (currentItems.isEmpty()) {
				Notification.show("No matching results found.", 3000, Notification.Position.MIDDLE);
			}
		});

		// Clear Filters Button
		Button clearFiltersButton = new Button("Clear Filters", e -> {
			studentFilter.clear();
			startDate.clear();
			endDate.clear();
			fetchAndDisplayData();
		});

		Button exportButton = new Button("Export Data", event -> GridExporter.newWithDefaults(this.tcsGrid).open());

		filtersLayout.add(studentFilter, startDate, endDate, applyFiltersButton, clearFiltersButton, exportButton);
		return filtersLayout;
	}

	private void configureGrid() {
		tcsGrid.removeAllColumns();

		// Student's Name Column
		tcsGrid.addColumn(tcsForm -> {
			if (tcsForm.getAssociatedStudent() != null) {
				return tcsForm.getAssociatedStudent().getLastName() + ", "
						+ tcsForm.getAssociatedStudent().getFirstName();
			} else {
				return "N/A";
			}
		}).setHeader("Student's Name").setKey("studentName");

		// Guardian Name Column
		tcsGrid.addColumn(TcsForm::getGuardianName).setHeader("Guardian Name").setKey("guardianName");

		// Date Column
		tcsGrid.addColumn(
				tcsForm -> tcsForm.getDateTimeOfVisit() != null ? tcsForm.getDateTimeOfVisit().toLocalDate().toString()
						: "N/A")
				.setHeader("Date").setKey("date");

		// Time Column
		tcsGrid.addColumn(
				tcsForm -> tcsForm.getDateTimeOfVisit() != null ? tcsForm.getDateTimeOfVisit().toLocalTime().toString()
						: "N/A")
				.setHeader("Time").setKey("time");

		tcsGrid.setSizeFull();
	}

	private void fetchAndDisplayData() {
		currentItems = tcsFormService.findAll();
		tcsGrid.setItems(currentItems);
	}

	@Override
	protected byte[] generateExcel(List<CaresForm> items) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getItemId(CaresForm item) {
		// TODO Auto-generated method stub
		return null;
	}
}
