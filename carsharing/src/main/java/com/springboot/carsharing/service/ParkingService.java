package com.springboot.carsharing.service;

import com.springboot.carsharing.entity.Parking;

import java.util.List;

public interface ParkingService {
    public List<Parking> showAllParkings();
    public void saveParking(Parking parking);
    public Parking getParking (int id);
    public void deleteParking(int id);
}
