package com.springboot.carsharing.repository;

import com.springboot.carsharing.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {
}
