package com.springboot.carsharing.repository;

import com.springboot.carsharing.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findDriverByEmail(String email);
}

