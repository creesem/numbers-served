package com.canopy.numbers.served.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canopy.numbers.served.application.data.Reason;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {
}