package com.canopy.numbers.served.application.views.reports;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.data.CaresFormReason;
import com.canopy.numbers.served.application.service.CaresFormLocationService;
import com.canopy.numbers.served.application.service.CaresFormReasonService;
import com.canopy.numbers.served.application.service.CaresFormService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.xdev.vaadin.grid_exporter.GridExporter;

@Component
public class CaresReportLayout extends DownloadGridView<CaresForm> {

	private static final long serialVersionUID = -1114611788890403198L;

	@Autowired
	private CaresFormLocationService locationService;

	@Autowired
	private CaresFormReasonService reasonService;

	@Autowired
	private CaresFormService caresFormService; // Injecting the CaresFormService

	private Grid<CaresForm> caresGrid;

	private List<CaresForm> currentItems; // To store the currently displayed items

	public Div createLayout() {
		Div layout = new Div();
		layout.setSizeFull();

		// Filters section
		HorizontalLayout filtersLayout = new HorizontalLayout();
		filtersLayout.setWidthFull();

		ComboBox<String> locationFilter = new ComboBox<>("Location");
		List<CaresFormLocation> locations = locationService.getAllLocations();
		List<String> locationNames = locations.stream().map(CaresFormLocation::getName).collect(Collectors.toList());
		locationFilter.setItems(locationNames);
		locationFilter.setPlaceholder("Select Location");

		ComboBox<CaresFormReason> reasonFilter = new ComboBox<>("Reason for Visit");
		List<CaresFormReason> reasons = reasonService.getAllReasons();
		reasonFilter.setItems(reasons);
		reasonFilter.setPlaceholder("Select Reason");

		DatePicker startDate = new DatePicker("Start Date");
		DatePicker endDate = new DatePicker("End Date");

		Button applyFiltersButton = new Button("Apply Filters", event -> {
			// Retrieve selected filter values
			String selectedLocation = locationFilter.getValue();
			CaresFormReason selectedReason = reasonFilter.getValue();
			LocalDate start = startDate.getValue();
			LocalDate end = endDate.getValue();

			// Fetch all forms and apply filters
			currentItems = caresFormService.findAll().stream()
					.filter(form -> (selectedLocation == null
							|| (form.getLocation() != null && selectedLocation.equals(form.getLocation().getName()))))
					.filter(form -> (selectedReason == null
							|| (form.getReasonForVisit() != null && selectedReason.equals(form.getReasonForVisit()))))
					.filter(form -> (start == null || (form.getDateTimeOfVisit() != null
							&& !form.getDateTimeOfVisit().toLocalDate().isBefore(start))))
					.filter(form -> (end == null || (form.getDateTimeOfVisit() != null
							&& !form.getDateTimeOfVisit().toLocalDate().isAfter(end))))
					.collect(Collectors.toList());

			// Update grid with filtered data
			caresGrid.setItems(currentItems);

			// Show notification if no data matches the filters
			if (currentItems.isEmpty()) {
				Notification.show("No matching results found.", 3000, Notification.Position.MIDDLE);
			}
		});

		Button clearFiltersButton = new Button("Clear Filters", e -> {
			locationFilter.clear();
			reasonFilter.clear();
			startDate.clear();
			endDate.clear();
			// Reset grid to show all items
			currentItems = caresFormService.findAll();
			caresGrid.setItems(currentItems);
		});

		filtersLayout.add(locationFilter, reasonFilter, startDate, endDate, applyFiltersButton, clearFiltersButton);

		// Table to display report data
		caresGrid = new Grid<>(CaresForm.class);
		caresGrid.setSizeFull();
		caresGrid.setColumns("visitorName", "associatedStudent", "dateTimeOfVisit", "location", "reasonForVisit");

		// Fetch data from the service and set it to the grid
		currentItems = caresFormService.findAll();
		caresGrid.setItems(currentItems);

		// Export Button
		Button exportButton = new Button("Export Data", event -> GridExporter.newWithDefaults(this.caresGrid).open());
		filtersLayout.add(exportButton);
		layout.add(filtersLayout, caresGrid);
		layout.getStyle().set("background-color", "#f9f9f9");

		return layout;
	}

	protected byte[] generateExcel(CaresForm item) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			var sheet = workbook.createSheet("Report");
			var headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Visitor Name");
			headerRow.createCell(1).setCellValue("Associated Student");
			headerRow.createCell(2).setCellValue("Date Time of Visit");
			headerRow.createCell(3).setCellValue("PRTF/CARES School");
			headerRow.createCell(4).setCellValue("Reason for Visit");

			var dataRow = sheet.createRow(1);
			dataRow.createCell(0).setCellValue(item.getVisitorName());
			dataRow.createCell(1).setCellValue(
					(item.getAssociatedStudent().getLastName() + ", " + item.getAssociatedStudent().getFirstName()));
			dataRow.createCell(2).setCellValue(item.getDateTimeOfVisit().toString());
			dataRow.createCell(3).setCellValue(item.getLocation().toString());
			dataRow.createCell(4).setCellValue(item.getReasonForVisit().toString());

			workbook.write(out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getItemId(CaresForm item) {
		return item.getId().toString();
	}

	@Override
	protected byte[] generateExcel(List<CaresForm> items) {
		// TODO Auto-generated method stub
		return null;
	}
}
