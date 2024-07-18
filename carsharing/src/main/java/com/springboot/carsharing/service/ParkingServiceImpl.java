package com.springboot.carsharing.service;

import com.springboot.carsharing.entity.Parking;
import com.springboot.carsharing.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public List<Parking> showAllParkings() {
        return parkingRepository.findAll();
    }

    @Override
    public void saveParking(Parking parking) {
        parkingRepository.save(parking);
    }

    @Override
    public Parking getParking(int id) {
        return parkingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteParking(int id) {
        parkingRepository.deleteById(id);
    }
}
