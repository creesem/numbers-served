package com.canopy.numbers.served.application.views.reports;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canopy.numbers.served.application.data.TcsForm;
import com.canopy.numbers.served.application.service.TcsFormService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

@Component
public class TcsReportLayout extends DownloadGridView<TcsForm> {
	private static final long serialVersionUID = -2913318971443603770L;
	private final TcsFormService tcsFormService;
	private Grid<TcsForm> tcsGrid;
	private List<TcsForm> currentItems;

	@Autowired
	public TcsReportLayout(TcsFormService tcsFormService) {
		this.tcsFormService = tcsFormService;
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
			currentItems = tcsFormService.findAll().stream()
					.filter(form -> guardianName.isEmpty()
							|| form.getGuardianName().toLowerCase().contains(guardianName))
					.filter(form -> selectedDate == null || (form.getDateTimeOfVisit() != null
							&& form.getDateTimeOfVisit().toLocalDate().equals(selectedDate)))
					.filter(form -> studentName.isEmpty() || (form.getStudentFullname() != null
							&& (form.getStudentFullname().toLowerCase().contains(studentName))))
					.collect(Collectors.toList());

			tcsGrid.setItems(currentItems);

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
		tcsGrid = new Grid<>(TcsForm.class);
		tcsGrid.setSizeFull();
		tcsGrid.addColumn(TcsForm::getGuardianName).setHeader("Guardian Name").setSortable(true);
		tcsGrid.addColumn(
				tcsForm -> tcsForm.getDateTimeOfVisit() != null ? tcsForm.getDateTimeOfVisit().toLocalDate().toString()
						: "N/A")
				.setHeader("Date").setSortable(true);
		tcsGrid.addColumn(tcsForm -> {
			if (tcsForm.getStudentFullname() != null) {
				return tcsForm.getStudentFullname();
			}
			return "N/A";
		}).setHeader("Associated Student");

		fetchAndDisplayData();

		layout.add(filtersLayout, tcsGrid);
		return layout;
	}

	private void fetchAndDisplayData() {
		currentItems = tcsFormService.findAll();
		tcsGrid.setItems(currentItems);
	}

	@Override
	protected byte[] generateExcel(List<TcsForm> items) {
		return null;
	}

	@Override
	protected String getItemId(TcsForm item) {
		return item.getId().toString();
	}
}
