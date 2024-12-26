package com.canopy.numbers.served.application.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.canopy.numbers.served.application.data.NumbersServedStudent;
import com.canopy.numbers.served.application.service.NumbersServedStudentService;

@RestController
@RequestMapping("/students")
public class NumbersServedStudentController {

	@Autowired
	private NumbersServedStudentService studentService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
		try {
			List<NumbersServedStudent> students = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

			String line;
			boolean isFirstLine = true; // Skip header row
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

			while ((line = reader.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}

				String[] fields = line.split(",");
				NumbersServedStudent student = new NumbersServedStudent();
				student.setStudentId(Integer.parseInt(fields[0]));
				String[] nameParts = fields[1].split(" ");
				student.setLastName(nameParts[0].replace(",", "").trim());
				student.setFirstName(nameParts[1].trim());
				student.setStatus(fields[2].trim());
				student.setGradeLevel(fields[3].trim());
				student.setBirthDate(LocalDate.parse(fields[4].trim(), formatter));

				students.add(student);
			}

			studentService.saveAll(students);
			return ResponseEntity.ok("File uploaded and processed successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Failed to process file: " + e.getMessage());
		}
	}
}
