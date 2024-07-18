package com.springboot.carsharing.service;

import com.springboot.carsharing.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface CarService {
    List<Car> getAllCars(String model, Integer parkingId, String clas );
    List<Car> getCarsOnParking(int parking_id);
//    void saveCar(Car car, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException;
    void saveCar(Car car, MultipartFile file1, MultipartFile file2, MultipartFile file3, int costForMinute,
                    int parkingId, boolean isAvailable) throws IOException;
    Car getCar(int id);
    void deleteCar(int id);
}

