package com.canopy.numbers.served.application.views.reports;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canopy.numbers.served.application.data.PRTFForm;
import com.canopy.numbers.served.application.service.PTRFFormService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

@Component
public class PTRFReportLayout extends DownloadGridView<PRTFForm> {
	private static final long serialVersionUID = -2913318971443603770L;
	private final PTRFFormService ptrfFormService;
	private Grid<PRTFForm> ptrfGrid;
	private List<PRTFForm> currentItems;

	@Autowired
	public PTRFReportLayout(PTRFFormService ptrfFormService) {
		this.ptrfFormService = ptrfFormService;
		add(createLayout());
	}

	public Div createLayout() {
		Div layout = new Div();
		layout.setSizeFull();

		// Filters section
		HorizontalLayout filtersLayout = new HorizontalLayout();
		filtersLayout.setWidthFull();

		TextField guardianNameFilter = new TextField("Guardian Name");
		guardianNameFilter.setPlaceholder("Filter by Guardian Name");

		DatePicker dateFilter = new DatePicker("Date");
		dateFilter.setPlaceholder("Filter by Date");

		TextField associatedStudentFilter = new TextField("Associated Student");
		associatedStudentFilter.setPlaceholder("Filter by Student Name");

		Button applyFiltersButton = new Button("Apply Filters", event -> {
			String guardianName = guardianNameFilter.getValue().trim().toLowerCase();
			LocalDate selectedDate = dateFilter.getValue();
			String studentName = associatedStudentFilter.getValue().trim().toLowerCase();

			// Apply filters
			currentItems = ptrfFormService.findAll().stream()
					.filter(form -> guardianName.isEmpty()
							|| form.getGuardianName().toLowerCase().contains(guardianName))
					.filter(form -> selectedDate == null || (form.getDateTimeOfVisit() != null
							&& form.getDateTimeOfVisit().toLocalDate().equals(selectedDate)))
					.filter(form -> studentName.isEmpty() || (form.getStudentFullname() != null
							&& (form.getStudentFullname().toLowerCase().contains(studentName))))
					.collect(Collectors.toList());

			ptrfGrid.setItems(currentItems);

			if (currentItems.isEmpty()) {
				Notification.show("No matching results found.", 3000, Notification.Position.MIDDLE);
			}
		});

		Button clearFiltersButton = new Button("Clear Filters", event -> {
			guardianNameFilter.clear();
			dateFilter.clear();
			associatedStudentFilter.clear();
			fetchAndDisplayData();
		});

		filtersLayout.add(guardianNameFilter, dateFilter, associatedStudentFilter, applyFiltersButton,
				clearFiltersButton);

		// Grid for displaying TcsForm data
		ptrfGrid = new Grid<>(PRTFForm.class);
		ptrfGrid.setSizeFull();
		ptrfGrid.addColumn(PRTFForm::getGuardianName).setHeader("Guardian Name").setSortable(true);
		ptrfGrid.addColumn(
				tcsForm -> tcsForm.getDateTimeOfVisit() != null ? tcsForm.getDateTimeOfVisit().toLocalDate().toString()
						: "N/A")
				.setHeader("Date").setSortable(true);
		ptrfGrid.addColumn(tcsForm -> {
			if (tcsForm.getStudentFullname() != null) {
				return tcsForm.getStudentFullname();
			}
			return "N/A";
		}).setHeader("Associated Student");

		fetchAndDisplayData();

		layout.add(filtersLayout, ptrfGrid);
		return layout;
	}

	private void fetchAndDisplayData() {
		currentItems = ptrfFormService.findAll();
		ptrfGrid.setItems(currentItems);
	}

	@Override
	protected byte[] generateExcel(List<PRTFForm> items) {
		return null;
	}

	@Override
	protected String getItemId(PRTFForm item) {
		return item.getId().toString();
	}
}
