package com.canopy.numbers.served.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canopy.numbers.served.application.data.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
