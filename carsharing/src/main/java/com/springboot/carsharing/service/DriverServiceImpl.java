package com.springboot.carsharing.service;

import com.springboot.carsharing.entity.Image;
import com.springboot.carsharing.enums.Role;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.repository.CarRepository;
import com.springboot.carsharing.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.springboot.carsharing.entity.Image.toImageEntity;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Driver> getAllDrivers(String email) {
        if (email != null) {
            return driverRepository.findDriverByEmail(email).stream().toList();
        }
        return driverRepository.findAll();
    }

    @Override
    public void saveDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    public Driver getDriver(int id) {
        return driverRepository.findById(id).orElse(null);
    }

    @Override
    public Car getDriverCar(int carId) {
        return carRepository.findById(carId).orElse(null);
    }

    @Override
    public void deleteDriver(int id) {
        driverRepository.deleteById(id);
    }

    @Override
    public boolean registerUser(Driver driver, MultipartFile file1) throws IOException {

        String email = driver.getEmail();
        String encodedPassword = passwordEncoder.encode(driver.getPassword());
        if (driverRepository.findDriverByEmail(email).isPresent()) {
//            throw new NoSuchCarException("Username already exists: " + driver.getEmail());
        }
        driver.setActive(true);
        driver.setPassword(encodedPassword);
        driver.setRole("user");
        log.info("Saving a new Driver with email: {}", email);
        Driver driver2 = driverRepository.save(driver);
        if(file1.getSize()!=0){
            Image image1 = toImageEntity(file1);
            driver2.addImageToDriver(image1);
            imageRepository.save(image1);
            driverRepository.save(driver2);
        }
        return true;
    }
}
