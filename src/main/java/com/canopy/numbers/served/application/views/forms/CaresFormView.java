package com.canopy.numbers.served.application.views.forms;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.CaresFormLocation;
import com.canopy.numbers.served.application.service.CaresFormLocationService;
import com.canopy.numbers.served.application.service.CaresFormReasonService;
import com.canopy.numbers.served.application.service.CaresFormService;
import com.canopy.numbers.served.application.service.NumbersServedStudentService;
import com.canopy.numbers.served.application.views.layout.NoNavLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "tcs-form", layout = NoNavLayout.class)
@PageTitle("CARES Check-in")
@AnonymousAllowed
public class CaresFormView extends VerticalLayout {
	private static final long serialVersionUID = 3586355313266457400L;
	private final CaresFormService caresFormService;
	private final TextField associatedStudent;
	private final Select<CaresFormLocation> location;
	private final Select<String> reasonForVisit;
	private final TextField visitorName;
	private final TextField otherReasonField; // Field for custom reason

	@Autowired
	public CaresFormView(CaresFormService caresFormService, CaresFormLocationService caresFormLocationService,
			CaresFormReasonService caresFormReasonService, NumbersServedStudentService numbersServedStudentService) {
		this.caresFormService = caresFormService;

		// Initialize components
		associatedStudent = new TextField("Associated Student");

		location = new Select<>();
		reasonForVisit = new Select<>();

		visitorName = new TextField("Visitor Name");

		List<String> reasonOptions = caresFormReasonService.findAll().stream().map(reason -> reason.getName())
				.collect(Collectors.toList());
		// reasonOptions.add("Other"); // Add 'Other' option

		// Populate dropdowns
		location.setLabel("Location");
		location.setItems(caresFormLocationService.findAll());
		location.setItemLabelGenerator(CaresFormLocation::getName);

		reasonForVisit.setLabel("Reason for Visit");
		reasonForVisit.setItems(reasonOptions);

		// "Other" reason text field (hidden initially)
		otherReasonField = new TextField("Specify Other Reason");
		otherReasonField.setVisible(false);

		reasonForVisit.addValueChangeListener(event -> {
			if ("Other".equals(event.getValue())) {
				otherReasonField.setVisible(true);
			} else {
				otherReasonField.setVisible(false);
				otherReasonField.clear();
			}
		});

		// Form layout
		FormLayout formLayout = new FormLayout(visitorName, associatedStudent, location, reasonForVisit,
				otherReasonField);

		// Buttons
		Button saveButton = new Button("Save", e -> saveCaresForm());
		Button cancelButton = new Button("Cancel", e -> clearForm());

		HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

		// Container for form and title
		VerticalLayout formContainer = new VerticalLayout();
		formContainer.setWidth("400px");
		formContainer.setPadding(true);
		formContainer.setSpacing(true);
		formContainer.getStyle().set("border", "1px solid #ccc");
		formContainer.getStyle().set("box-shadow",
				"0 4px 24px -3px rgba(0, 0, 0, 0.2), 0 18px 64px -8px rgba(0, 0, 0, 0.4)");
		formContainer.getStyle().set("border-radius", "8px");
		formContainer.getStyle().set("background-color", "#fff");

		// Title
		H2 title = new H2("CARES Check-in");
		title.getStyle().set("text-align", "center");

		// Add components to form container
		formContainer.add(title, formLayout, buttonLayout);

		// Centering container
		Div centeringDiv = new Div(formContainer);
		centeringDiv.getStyle().set("display", "flex");
		centeringDiv.getStyle().set("justify-content", "center");
		centeringDiv.getStyle().set("align-items", "center");
		centeringDiv.getStyle().set("height", "100%");
		centeringDiv.getStyle().set("width", "100%");
		centeringDiv.getStyle().set("position", "absolute");
		centeringDiv.getStyle().set("top", "0");
		centeringDiv.getStyle().set("left", "0");
		centeringDiv.getStyle().set("background-color", "#f5f5f5");

		// Ensure the main layout occupies full space
		setSizeFull();
		getStyle().set("display", "flex");
		getStyle().set("justify-content", "center");
		getStyle().set("align-items", "center");

		// Add to main layout
		add(centeringDiv);
	}

	private void saveCaresForm() {
		if (isValidInput()) {
			CaresForm form = new CaresForm();
			form.setVisitorName(visitorName.getValue());
			form.setStudentFullname(associatedStudent.getValue());
			form.setDateTimeOfVisit(LocalDateTime.now()); // Set current date and time
			form.setLocation(location.getValue());
			if (otherReasonField.isVisible()) {
				form.setReasonForVisit(otherReasonField.getValue());
			} else {
				form.setReasonForVisit(reasonForVisit.getValue());
			}
			caresFormService.save(form);
			Notification.show("Form saved successfully!", 3000, Notification.Position.MIDDLE);
			clearForm();
		} else {
			Notification.show("Please fill out all fields.", 3000, Notification.Position.MIDDLE);
		}
	}

	private boolean isValidInput() {
		return !visitorName.isEmpty() && associatedStudent.getValue() != null && location.getValue() != null
				&& reasonForVisit.getValue() != null;
	}

	private void clearForm() {
		associatedStudent.clear();
		location.clear();
		reasonForVisit.clear();
		visitorName.clear();
	}
}
