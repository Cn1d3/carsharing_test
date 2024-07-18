package com.springboot.carsharing.repository;

import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findCarByModel(String model);
    List<Car> findCarByClas(String clas);
    @Query("from Car car WHERE car.parkingOfCar.id = :id")
    List<Car> findCarByParkingOfCar(int id);
    @Query("from Car car WHERE car.parkingOfCar.id = :id OR car.model = :model OR car.clas = :clas")
    List<Car> findCarByModelAndParkingOfCarAndClas(String model, int id, String clas);
    @Query("from Car car WHERE car.parkingOfCar.id = :id OR car.model = :model")
    List<Car> findCarByModelAndParkingOfCar(String model, int id);
    @Query("from Car car WHERE car.parkingOfCar.id = :id OR car.clas = :clas")
    List<Car> findCarByParkingOfCarAndClas(int id, String clas);

    List<Car> findCarByModelAndClas(String model, String clas);
}
