package com.springboot.carsharing.service;


import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.entity.Parking;
import com.springboot.carsharing.entity.Rent;
import com.springboot.carsharing.repository.CarRepository;
import com.springboot.carsharing.repository.ParkingRepository;
import com.springboot.carsharing.repository.RentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Rent> getRent() {
        return rentRepository.findAll();
    }

    @Override
    public Rent getRent(int id) {
        return rentRepository.findById(id).orElse(null);
    }

    @Override
    public Rent saveRent(Rent rent) {
        return rentRepository.save(rent);
    }

    @Override
    public void startRent(Rent rent, Driver driver) {
        Car selectedCar = carRepository.findById(rent.getOrderedCar().getId()).orElseThrow(() -> new RuntimeException("Car not found"));
        rent.setStartTime(LocalDateTime.now());
        rent.setStartPoint(selectedCar.getParkingOfCar().getAddress());
        rent.setDriver(driver);
        selectedCar.setAvailable(false);
        carRepository.save(selectedCar);
        rentRepository.save(rent);
    }

    @Override
    public void stopRent(Rent rent) {
        Car selectedCar = rent.getOrderedCar();
        selectedCar.setAvailable(true);
        rent.setEndTime(LocalDateTime.now());
        carRepository.save(selectedCar);
        rentRepository.save(rent);
    }

    @Override
    public List<Rent> getRentHistory(Driver driver) {
        return rentRepository.findRentByDriverId(driver.getId())
                .stream().peek(Rent::getTotalTime).toList();
    }

    public Car getCarInfoByRentId(int rentId) {
        Rent rent = rentRepository.findById(rentId).orElse(null);
        if (rent != null) {
            return rent.getOrderedCar();
        }
        return null;
    }
}
