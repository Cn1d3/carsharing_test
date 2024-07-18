package com.springboot.carsharing.controller;
import com.springboot.carsharing.configs.MyUserDetails;
import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.entity.Parking;
import com.springboot.carsharing.entity.Rent;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.service.CarService;
import com.springboot.carsharing.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class RentController {
    @Autowired
    private RentService rentService;

    @Autowired
    private CarService carService;

//not used or used for rent history
    @GetMapping("/rents")
    public String getRent(Model model){
        List<Rent> rentList = rentService.getRent();
        model.addAttribute("rent", rentList);
        return "rentPage";
    }

//used for a new rent creation,  with the chosen car in parameters also
    @GetMapping("/rents/new")
    public String showStartRentForm(@RequestParam(name = "carId") int carId, Model model) {
        Car orderedCar = carService.getCar(carId);
        Rent rent = new Rent();
        rent.setOrderedCar(orderedCar);
        model.addAttribute("rent", rent);
        return "rentPage";
    }

    @PostMapping("/rents/start")
    public ResponseEntity<?> startRent(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam(name = "carId") int carId, Model model) {
        Car orderedCar = carService.getCar(carId);
        if (!orderedCar.isAvailable()) {
            return ResponseEntity.badRequest().body("Cannot start rent, car is already rented.");
        }
        Rent rent = new Rent();
        rent = rentService.saveRent(rent);
        rent.setOrderedCar(orderedCar);
        rentService.startRent(rent, userDetails.getDriver());
        userDetails.getDriver().getRents().add(rent);
        model.addAttribute("orderedCar", orderedCar);
        return ResponseEntity.ok().body(rent.getId());
    }

    @PostMapping("/rents/stop/{rentId}")
    public ResponseEntity<String> stopRent(@PathVariable int rentId) {
        Rent rent = rentService.getRent(rentId);
        rent.setEndTime(LocalDateTime.now());
        rentService.stopRent(rent);
        return ResponseEntity.ok().body("http://localhost:8080/rents/results/" + rentId
        );
    }

//    @PostMapping("/rents/stop/{rentId}")
//    public String stopRent(@PathVariable int rentId, Model model) {
//        Rent rent = rentService.getRent(rentId);
//        rent.setEndTime(LocalDateTime.now());
//        model.addAttribute("rent", rent);
//        rentService.stopRent(rent);
//        return "rent_results";
//    }

    @GetMapping("/rents/results/{rentId}")
    public String getResults(@PathVariable int rentId, Model model){
        Rent rent = rentService.getRent(rentId);
        model.addAttribute("rent", rent);
        Duration duration = Duration.between(rent.getEndTime(), rent.getStartTime()).abs();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        model.addAttribute("hours", hours);
        model.addAttribute("minutes", minutes);
        return "rent_results";
    }

    @GetMapping("/rents/current")
    public String getCurRent(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
        Driver driver = userDetails.getDriver();
        Rent rent = driver.getRents().get(driver.getRents().size()-1);
        model.addAttribute("rent", rent);
        return "rentPage";
    }

//used for showing driver`s rents
    @GetMapping("/rentHistory")
    public String getRentHistory(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
        Driver driver = userDetails.getDriver();
        List<Rent> rentHistory = rentService.getRentHistory(driver);
        model.addAttribute("rentHistory", rentHistory);
        return "rentHistory";
    }
}
