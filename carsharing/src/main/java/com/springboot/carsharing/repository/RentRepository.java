package com.springboot.carsharing.repository;

import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findRentByDriver(Driver driver);
    @Query("from Rent r WHERE r.driver.id = :id order by r.startTime desc")
    List<Rent> findRentByDriverId(int id);

}

