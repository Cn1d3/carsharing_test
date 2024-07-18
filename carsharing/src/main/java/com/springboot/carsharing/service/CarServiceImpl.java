package com.springboot.carsharing.service;


import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.entity.Image;
import com.springboot.carsharing.entity.Rent;
import com.springboot.carsharing.repository.CarRepository;
import com.springboot.carsharing.repository.DriverRepository;
import com.springboot.carsharing.repository.ParkingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.springboot.carsharing.entity.Image.toImageEntity;

@Service
@Slf4j
public class CarServiceImpl implements CarService {
    EntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ParkingRepository parkingRepository;
    @Override
    public List<Car> getAllCars(String model, Integer parkingId, String clas) {
        if (parkingId != null && model != null && clas != null) {
            return carRepository.findCarByModelAndParkingOfCarAndClas(model, parkingId, clas);
        } else if (parkingId != null && model != null) {
            return carRepository.findCarByModelAndParkingOfCar(model, parkingId);
        } else if (parkingId != null && clas != null) {
            return carRepository.findCarByParkingOfCarAndClas(parkingId, clas);
        } else if (model != null && clas != null) {
            return carRepository.findCarByModelAndClas(model, clas);
        } else if (parkingId != null) {
            return carRepository.findCarByParkingOfCar(parkingId);
        } else if (model != null) {
            return carRepository.findCarByModel(model);
        } else if (clas != null) {
            return carRepository.findCarByClas(clas);
        }
        return carRepository.findAll();
    }

    @Override
    public void saveCar(Car car, MultipartFile file1, MultipartFile file2, MultipartFile file3, int costForMinute,
    int parkingId, boolean isAvailable) throws IOException {
        Image image1;
        if(file1.getSize()!=0){
            image1 = toImageEntity(file1);
            image1.setPreview(true);
            car.addImageToCar(image1);
        }
        Image image2;
        if(file2.getSize()!=0){
            image2 = toImageEntity(file2);
            car.addImageToCar(image2);
        }
        Image image3;
        if(file3.getSize()!=0){
            image3 = toImageEntity(file3);
            car.addImageToCar(image3);
        }
        car.setCostForMinute(costForMinute);
        car.setParkingOfCar(parkingRepository.findById(parkingId).orElse(null));
        car.setAvailable(isAvailable);
        log.info("Car saved with parameters as: " + car.getModel() + ", " + car.getYear());
        Car carIm = carRepository.save(car);
        carIm.setPreviewImageId(carIm.getImageList().get(0).getId());
        carRepository.save(car);
    }

    @Override
    public List<Car> getCarsOnParking(int parkingId) {
        Query query = entityManager.createQuery("FROM Car WHERE parkingOfCar.id = :parkingId");
        query.setParameter("parkingId", parkingId);
        return query.getResultList();
    }
    @Override
    public Car getCar(int id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}
