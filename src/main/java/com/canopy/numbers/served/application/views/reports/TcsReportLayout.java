package com.canopy.numbers.served.application.views.reports;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canopy.numbers.served.application.data.TcsForm;
import com.canopy.numbers.served.application.service.TcsFormService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Component
@Route(value = "tcs-report")
public class TcsReportLayout extends VerticalLayout {

    private final TcsFormService tcsFormService;
    private Grid<TcsForm> tcsGrid;

    @Autowired
    public TcsReportLayout(TcsFormService tcsFormService) {
        this.tcsFormService = tcsFormService;
        initializeLayout();
    }

    private void initializeLayout() {
        setSizeFull();
        tcsGrid = new Grid<>(TcsForm.class);
        configureGrid();
        add(tcsGrid);
        fetchAndDisplayData();
    }

    private void configureGrid() {
        tcsGrid.removeAllColumns();

        // Student's Name Column
        tcsGrid.addColumn(tcsForm -> {
            if (tcsForm.getAssociatedStudent() != null) {
                return tcsForm.getAssociatedStudent().getLastName() + ", " + tcsForm.getAssociatedStudent().getFirstName();
            } else {
                return "N/A";
            }
        }).setHeader("Student's Name").setKey("studentName");

        // Guardian Name Column
        tcsGrid.addColumn(TcsForm::getGuardianName).setHeader("Guardian Name").setKey("guardianName");

        // Date Column
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tcsGrid.addColumn(tcsForm -> tcsForm.getDateTimeOfVisit() != null ? tcsForm.getDateTimeOfVisit().format(dateFormatter) : "N/A")
                .setHeader("Date").setKey("date");

        // Time Column
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        tcsGrid.addColumn(tcsForm -> tcsForm.getDateTimeOfVisit() != null ? tcsForm.getDateTimeOfVisit().format(timeFormatter) : "N/A")
                .setHeader("Time").setKey("time");

        tcsGrid.setSizeFull();
    }

    private void fetchAndDisplayData() {
        List<TcsForm> tcsForms = tcsFormService.findAll();
        tcsGrid.setItems(tcsForms);
    }
}
