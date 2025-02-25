package com.canopy.numbers.served.application.repository;

import com.canopy.numbers.served.application.data.PRTFForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PTRFFormRepository extends JpaRepository<PRTFForm, Long> {
    // Additional query methods (if needed) can be defined here
}
