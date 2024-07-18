package com.springboot.carsharing.controller;

import com.springboot.carsharing.configs.MyUserDetails;
import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Parking;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.service.CarService;
import com.springboot.carsharing.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private ParkingService parkingService;

    @GetMapping("/cars")
    public String showAllCars(@RequestParam(name = "model", required = false) String model,
                              @RequestParam(name = "address", required = false) Integer parkingId,
                              @RequestParam(name = "clas", required = false) String clas,
                              Model mod) {
        List<Car> allCars = carService.getAllCars(model, parkingId, clas);

        List<Parking> parkings = parkingService.showAllParkings();
        Set<String> uniqueClasses = allCars.stream()
                .map(Car::getClas)
                .collect(Collectors.toSet());

        mod.addAttribute("cars", allCars);
        mod.addAttribute("parkings", parkings);
        mod.addAttribute("uniqueClasses", uniqueClasses);
        return "allCars";
    }

    @GetMapping("/cars/{id}")
    public String getCar(@PathVariable int id, Model model) {
        Car car = carService.getCar(id);
        if (car == null) {
            throw new NoSuchCarException("There is no car with id = " + id);
        }
        model.addAttribute("car", car);
        model.addAttribute("images", car.getImageList());
        return "carInfo";
    }

    @GetMapping("/cars/parking/{parking_id}")
    public String getCarsOnParking(@PathVariable int parking_id, Model model) {
        List<Car> carsOnParking = carService.getCarsOnParking(parking_id);
        if (carsOnParking.isEmpty()) {
            throw new NoSuchCarException("No cars on parking with id = " + parking_id);
        }
        model.addAttribute("carsOnParking", carsOnParking);
        return "allCars";
    }

    //ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_ADMIN_

    @GetMapping("/cars/control")
    public String showAllCars(@RequestParam(name = "model", required = false) String model,
                              @RequestParam(name = "address", required = false) Integer parkingId,
                              @RequestParam(name = "clas", required = false) String clas,
                              Model mod, @AuthenticationPrincipal MyUserDetails details) {

        List<Car> allCars = carService.getAllCars(model, parkingId, clas);

        List<Parking> parkings = parkingService.showAllParkings();
        Set<String> uniqueClasses = allCars.stream()
                .map(Car::getClas)
                .collect(Collectors.toSet());

        mod.addAttribute("cars", allCars);
        mod.addAttribute("parkings", parkings);
        mod.addAttribute("uniqueClasses", uniqueClasses);
        mod.addAttribute("driver", details.getDriver());
        return "carControl";
    }


    @GetMapping("/cars/new")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "carAdding";
    }

    @PostMapping("/cars/new")
    public String addNewCar(@ModelAttribute Car car,
                            @RequestParam("file1") MultipartFile file1,
                            @RequestParam("file2") MultipartFile file2,
                            @RequestParam("file3") MultipartFile file3,
                            @RequestParam("costForMinute") int costForMinute,
                            @RequestParam("parkingId") int parkingId,
                            @RequestParam("isAvailable") boolean isAvailable) throws IOException {
        carService.saveCar(car, file1, file2, file3, costForMinute, parkingId, isAvailable);
        return "redirect:/cars";
    }


    @PostMapping("/cars/edit/{id}")
    public String showEditCarForm(@PathVariable int id, Model model) {
        Car car = carService.getCar(id);
        if (car == null) {
            throw new NoSuchCarException("There is no car with id = " + id);
        }
        model.addAttribute("car", car);
        return "redirect:/cars/new";
    }

    @PostMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable int id, Model model) {
        Car car = carService.getCar(id);
        if (car == null) {
            throw new NoSuchCarException("There is no car with id = " + id);
        }
        carService.deleteCar(id);
        model.addAttribute("message", "Car with id = " + id + " was deleted");
        return "redirect:/cars/control";
    }
}
