package com.canopy.numbers.served.application.views.reports;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

import com.canopy.numbers.served.application.data.CaresForm;
import com.canopy.numbers.served.application.data.NumbersServedStudent;
import com.canopy.numbers.served.application.service.CaresFormService;
import com.canopy.numbers.served.application.service.NumbersServedStudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("students-upload")
@AnonymousAllowed
public class NumbersServedStudentUploadView extends VerticalLayout {
	private static final long serialVersionUID = 3499519330520576970L;

	@Autowired
	private NumbersServedStudentService studentService;

	@Autowired
	private CaresFormService caresFormService;

	private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
	private final List<String> uploadedFiles = new ArrayList<>();

	public NumbersServedStudentUploadView() {
		setSizeFull();
		setSpacing(true);

		// Header for instructions
		Div uploadInstructions = new Div();
		uploadInstructions.setText("Upload one or more CSV files to add students to the database:");

		// Multi-file upload
		Upload upload = new Upload(buffer);
		upload.setAcceptedFileTypes(".csv");
		upload.addSucceededListener(event -> {
			uploadedFiles.add(event.getFileName());
			Notification.show("File '" + event.getFileName() + "' uploaded successfully!", 3000,
					Notification.Position.MIDDLE);
		});

		// Process and upload button
		Button processButton = new Button("Process and Upload Files");
		processButton.addClickListener(e -> processAndUploadFiles());

		// Add components to layout
		add(uploadInstructions, upload, processButton);
	}

	private void processAndUploadFiles() {
		if (uploadedFiles.isEmpty()) {
			Notification.show("No files uploaded to process!", 3000, Notification.Position.MIDDLE);
			return;
		}

		for (String fileName : uploadedFiles) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(buffer.getInputStream(fileName)))) {
				List<NumbersServedStudent> students = new ArrayList<>();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

				// Build the CSV format with headers
				CSVFormat csvFormat = CSVFormat.Builder.create()
						.setHeader("Student ID (System)", "LastName, FirstName", "Status", "Grade Level", "Birth date")
						.setSkipHeaderRecord(true).build();

				// Parse the CSV file
				try (CSVParser parser = csvFormat.parse(reader)) {
					for (CSVRecord record : parser) {
						NumbersServedStudent student = new NumbersServedStudent();

						// Parse fields
						student.setStudentId(Integer.parseInt(record.get("Student ID (System)").trim()));
						String[] nameParts = record.get("LastName, FirstName").split(", ");
						if (nameParts.length == 2) {
							student.setLastName(nameParts[0].trim());
							student.setFirstName(nameParts[1].trim());
						} else {
							student.setLastName(record.get("LastName, FirstName").trim());
							student.setFirstName(""); // Handle missing first name
						}
						student.setStatus(record.get("Status").trim());
						student.setGradeLevel(record.get("Grade Level").trim());
						student.setBirthDate(LocalDate.parse(record.get("Birth date").trim(), formatter));

						students.add(student);

					}
				}

				// Save all students to the database
				studentService.saveAll(students);
				Notification.show("File '" + fileName + "' processed and data uploaded!", 3000,
						Notification.Position.MIDDLE);

			} catch (Exception ex) {
				Notification.show("Error processing file '" + fileName + "': " + ex.getMessage(), 5000,
						Notification.Position.MIDDLE);
				ex.printStackTrace();
			}
		}

		// Clear uploaded files after processing
		uploadedFiles.clear();
	}

}
