package com.canopy.numbers.served.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.application.data.TcsForm;
import com.canopy.numbers.served.application.repository.TcsFormRepository;

@Service
public class TcsFormService {

    private final TcsFormRepository tcsFormRepository;

    @Autowired
    public TcsFormService(TcsFormRepository tcsFormRepository) {
        this.tcsFormRepository = tcsFormRepository;
    }

    public List<TcsForm> findAll() {
        return tcsFormRepository.findAll();
    }

    public Optional<TcsForm> findById(Long id) {
        return tcsFormRepository.findById(id);
    }

    public TcsForm save(TcsForm tcsForm) {
        return tcsFormRepository.save(tcsForm);
    }

    public void deleteById(Long id) {
        tcsFormRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}
