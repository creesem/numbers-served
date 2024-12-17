package com.canopy.numbers.served.application.views.reports;

import com.canopy.numbers.served.application.services.CanopySchoolFormService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CanopySchoolFormView extends VerticalLayout {

	private final CanopySchoolFormService canopySchoolFormService;
	private static final long serialVersionUID = 3908505815581125631L;
	public CanopySchoolFormView(CanopySchoolFormService canopySchoolFormService){
		this.canopySchoolFormService = canopySchoolFormService;
	}
}
