package com.canopy.numbers.served.application.views.reports;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.data.CaresFormReason;
import com.canopy.numbers.served.application.service.LocationService;
import com.canopy.numbers.served.application.service.ReasonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@Component
public class CaresReportLayout extends DownloadGridView<CaresForm> {

	private static final long serialVersionUID = -1114611788890403198L;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ReasonService reasonService;

	private Grid<CaresForm> caresGrid;

	public Div createLayout() {
		Div layout = new Div();
		layout.setSizeFull();

		// Filters section
		HorizontalLayout filtersLayout = new HorizontalLayout();
		filtersLayout.setWidthFull();

		ComboBox<String> locationFilter = new ComboBox<>("Location");
		List<CaresFormLocation> locations = locationService.getAllLocations();
		List<String> locationNames = locations.stream().map(CaresFormLocation::getName).toList();
		locationFilter.setItems(locationNames);
		locationFilter.setPlaceholder("Select Location");

		ComboBox<CaresFormReason> reasonFilter = new ComboBox<>("Reason for Visit");
		List<CaresFormReason> reasons = reasonService.getAllReasons();
		reasonFilter.setItems(reasons);
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
		caresGrid = new Grid<>(CaresForm.class);
		caresGrid.setSizeFull();
		caresGrid.setColumns("visitorName", "associatedStudent", "dateTimeOfVisit", "location", "reasonForVisit");

		// Download Button
		Button downloadButton = new Button("Download Excel");
		downloadButton.addClickListener(e -> {
			CaresForm selectedItem = caresGrid.asSingleSelect().getValue();
			if (selectedItem != null) {
				callDownloadService(selectedItem);
			} else {
				Notification.show("Please select an item to download.", 3000, Notification.Position.MIDDLE);
			}
		});

		// Add components to layout
		filtersLayout.add(downloadButton);
		layout.add(filtersLayout, caresGrid);
		layout.getStyle().set("background-color", "#f9f9f9");

		return layout;
	}

	@Override
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
			dataRow.createCell(1).setCellValue(item.getAssociatedStudent());
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
}
