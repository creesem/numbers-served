package com.canopy.numbers.served.application.views.reports;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route("reports")
public class ReportsView extends VerticalLayout {

	private static final long serialVersionUID = -4938342493569169286L;

	public ReportsView() {
		setSizeFull();

		// Create tabs for CARES and TCS reports
		Tab caresTab = new Tab("CARES Report");
		Tab tcsTab = new Tab("TCS Report");
		Tabs reportTabs = new Tabs(caresTab, tcsTab);
		reportTabs.setWidthFull();

		// Create containers for content
		Div caresContent = new CaresReportLayout().createLayout();
		Div tcsContent = createTcsReportLayout();

		// Wrap the content in a layout
		caresContent.setVisible(true);
		tcsContent.setVisible(false);

		// Switch content based on selected tab
		reportTabs.addSelectedChangeListener(event -> {
			boolean isCaresSelected = event.getSelectedTab().equals(caresTab);
			caresContent.setVisible(isCaresSelected);
			tcsContent.setVisible(!isCaresSelected);
		});

		// Add components to layout
		add(reportTabs, caresContent, tcsContent);
	}

	private Div createTcsReportLayout() {
		Div layout = new Div();
		layout.setText("TCS Report Layout"); // Placeholder text
		layout.setSizeFull();
		layout.getStyle().set("background-color", "#e9e9e9");
		return layout;
	}
}
