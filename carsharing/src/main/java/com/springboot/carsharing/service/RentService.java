package com.springboot.carsharing.service;

import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.entity.Rent;

import java.util.List;

public interface RentService {
    public List<Rent> getRent();
    public Rent getRent(int id);
    public Rent saveRent(Rent rent);
//    public Integer startRent(Rent rent, Driver driver);

    public void  startRent(Rent rent, Driver driver);
    public void stopRent(Rent rent);
    public List<Rent> getRentHistory(Driver driver);
    public Car getCarInfoByRentId(int rentId);
}
