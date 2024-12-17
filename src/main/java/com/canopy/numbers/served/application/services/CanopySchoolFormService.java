package com.canopy.numbers.served.application.services;

import java.util.List;

import com.canopy.numbers.served.application.data.CanopySchoolForm;

public interface CanopySchoolFormService {
	List<CanopySchoolForm> findAll();
	CanopySchoolForm save(CanopySchoolForm canopySchoolForm);

}
