package com.canopy.numbers.served.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.PRTFForm;
import com.canopy.numbers.served.application.repository.PTRFFormRepository;

@Service
public class PTRFFormService {

    private final PTRFFormRepository ptrfFormRepository;

    @Autowired
    public PTRFFormService(PTRFFormRepository tcsFormRepository) {
        this.ptrfFormRepository = tcsFormRepository;
    }

    public List<PRTFForm> findAll() {
        return ptrfFormRepository.findAll();
    }

    public Optional<PRTFForm> findById(Long id) {
        return ptrfFormRepository.findById(id);
    }

    public PRTFForm save(PRTFForm tcsForm) {
        return ptrfFormRepository.save(tcsForm);
    }

    public void deleteById(Long id) {
        ptrfFormRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}
