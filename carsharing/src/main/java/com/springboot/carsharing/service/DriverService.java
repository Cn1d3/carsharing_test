package com.springboot.carsharing.service;

import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DriverService {
    List<Driver> getAllDrivers(String email);

    void saveDriver(Driver driver);

    Driver getDriver(int id);

    Car getDriverCar(int car_id);

    void deleteDriver(int id);

    boolean registerUser(Driver driver, MultipartFile file1) throws IOException;
}
