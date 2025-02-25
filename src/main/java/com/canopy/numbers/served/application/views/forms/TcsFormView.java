package com.canopy.numbers.served.application.views.forms;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.canopy.numbers.served.application.data.CaresFormReason;
import com.canopy.numbers.served.application.data.PRTFForm;
import com.canopy.numbers.served.application.service.CaresFormLocationService;
import com.canopy.numbers.served.application.service.CaresFormReasonService;
import com.canopy.numbers.served.application.service.NumbersServedStudentService;
import com.canopy.numbers.served.application.service.PTRFFormService;
import com.canopy.numbers.served.application.views.layout.NoNavLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "prtf-form", layout = NoNavLayout.class)
@PageTitle("PTRF Check-in")
@AnonymousAllowed
public class TcsFormView extends VerticalLayout {
	private static final long serialVersionUID = -2250637917523294052L;
	private final TextField associatedStudent;
	private final PTRFFormService tcsFormService;
	private TextField guardianNameField = new TextField("Guardian Name");
	private final Select<String> reasonForVisit;
	private final TextField otherReasonField; // Field for custom reason

	@Autowired
	public TcsFormView(PTRFFormService tcsFormService, NumbersServedStudentService numbersServedStudentService,
			CaresFormReasonService caresFormReasonService, CaresFormLocationService caresFormLocationService) {
		// Create form components
		this.tcsFormService = tcsFormService;

		guardianNameField = new TextField("Guardian Name");

		// Populate the ComboBox with students
		associatedStudent = new TextField("Associated Student");

		List<String> reasonOptions = caresFormReasonService.findAll().stream().map(reason -> reason.getName())
				.collect(Collectors.toList());
		// reasonOptions.add("Other"); // Add 'Other' option

		reasonForVisit = new Select<>();
		reasonForVisit.setLabel("Reason for Visit");
		reasonForVisit.setItems(reasonOptions);

		// "Other" reason text field (hidden initially)
		otherReasonField = new TextField("Specify Other Reason");
		otherReasonField.setVisible(false);

		// Create a binder to bind the form fields to the TcsForm object
		Binder<PRTFForm> binder = new Binder<>(PRTFForm.class);
		binder.forField(guardianNameField).bind(PRTFForm::getGuardianName, PRTFForm::setGuardianName);
		binder.forField(associatedStudent).bind(PRTFForm::getStudentFullname, PRTFForm::setStudentFullname);
		binder.forField(reasonForVisit).bind(PRTFForm::getReasonForVisit, PRTFForm::setReasonForVisit);

		reasonForVisit.addValueChangeListener(event -> {
			if ("Other".equals(event.getValue())) {
				otherReasonField.setVisible(true);
			} else {
				otherReasonField.setVisible(false);
				otherReasonField.clear();
			}
		});

		// Create a form layout and add the fields
		FormLayout formLayout = new FormLayout();
		formLayout.add(guardianNameField, associatedStudent, reasonForVisit, otherReasonField);

		// Create Save and Cancel buttons
		Button saveButton = new Button("Save", event -> {
			if (binder.validate().isOk()) {
				PRTFForm tcsForm = new PRTFForm();
				binder.writeBeanIfValid(tcsForm);
				tcsForm.setDateTimeOfVisit(LocalDateTime.now());
				saveTcsForm(tcsForm); // Save the form data
			}
		});

		Button cancelButton = new Button("Cancel", event -> {
			binder.readBean(null); // Clear the form
		});

		// Add buttons to a HorizontalLayout for better alignment
		HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
		buttonLayout.setSpacing(true);

		// Title
		H2 title = new H2("TCS Check-in");
		title.getStyle().set("text-align", "center");

		VerticalLayout formContainer = new VerticalLayout();
		formContainer.setWidth("400px");
		formContainer.setPadding(true);
		formContainer.setSpacing(true);
		formContainer.getStyle().set("border", "1px solid #ccc");
		formContainer.getStyle().set("box-shadow",
				"0 4px 24px -3px rgba(0, 0, 0, 0.2), 0 18px 64px -8px rgba(0, 0, 0, 0.4)");
		formContainer.getStyle().set("border-radius", "8px");
		formContainer.getStyle().set("background-color", "#fff");

		formContainer.add(title, formLayout, buttonLayout);

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

		setSizeFull();
		getStyle().set("display", "flex");
		getStyle().set("justify-content", "center");
		getStyle().set("align-items", "center");

		// Add the form and buttons to the view
		add(centeringDiv);
	}

	private void saveTcsForm(PRTFForm tcsForm) {
		// Save the TcsForm object using the service
		if (otherReasonField.isVisible()) {
			tcsForm.setReasonForVisit(otherReasonField.getValue());
		}
		tcsFormService.save(tcsForm);
		clearForm();
		System.out.println("TcsForm saved: " + tcsForm);
	}

	private void clearForm() {
		// TODO Auto-generated method stub
		guardianNameField.clear();
		associatedStudent.clear();
		reasonForVisit.clear();
		otherReasonField.clear();
		otherReasonField.setVisible(false);
	}
}