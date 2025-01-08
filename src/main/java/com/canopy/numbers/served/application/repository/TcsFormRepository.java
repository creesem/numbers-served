package com.canopy.numbers.served.application.repository;

import com.canopy.numbers.served.application.data.TcsForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcsFormRepository extends JpaRepository<TcsForm, Long> {
    // Additional query methods (if needed) can be defined here
}
