package com.canopy.numbers.served.application.views.reports;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class FormsView extends VerticalLayout {
	private static final long serialVersionUID = -4369888541214711609L;

	private static VerticalLayout caresForm;
	private static VerticalLayout canopySchoolForm;

	public FormsView() {

		caresForm = new VerticalLayout();
		canopySchoolForm = new CanopySchoolFormView();

		Tabs tabs = createTabs();
		add(tabs, caresForm, canopySchoolForm);

	}

	private Tabs createTabs() {
		Tabs tabs = new Tabs();
		Tab caresTab = new Tab("Cares");
		Tab canopySchoolTab = new Tab("Canopy School");

		tabs.add(caresTab, canopySchoolTab);
		tabs.addSelectedChangeListener(event -> updateView(event.getSelectedTab()));

		return tabs;
	}

	private void updateView(Tab selectedTab) {
		hideAllForms();
		switch (selectedTab.getLabel()) {
		case "Users" -> showFormView(caresForm);
		case "Groups" -> showFormView(canopySchoolForm);
		}
	}

	private void hideAllForms() {
		caresForm.setVisible(false);
		canopySchoolForm.setVisible(false);
	}

	private void showFormView(VerticalLayout formView) {
		formView.setVisible(true);
	}

}
