package com.springboot.carsharing.controller;

import com.springboot.carsharing.entity.Parking;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/parking")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    @GetMapping("/parkings")
    public String showAllParkings(Model model){
        List<Parking> allParkings = parkingService.showAllParkings();
        model.addAttribute("parkings", allParkings);
        return "allParkings";
    }

    @GetMapping("/parkings/{id}")
    public String getParking(@PathVariable int id, Model model){
        Parking parking = parkingService.getParking(id);
        if (parking == null) {
            throw new NoSuchCarException("There is no parking with id = " + id);
        }
        model.addAttribute("parking", parking);
        return "parkingDetails";
    }

//    @GetMapping("/parkings/new")
//    public String showAddParkingForm(Model model){
//        model.addAttribute("parking", new Parking());
//        return "addParking";
//    }
//
//    @PostMapping("/parkings")
//    public String addNewParking(@ModelAttribute Parking parking){
//        parkingService.saveParking(parking);
//        return "redirect:/parking/parkings";
//    }

//    @GetMapping("/parkings/edit/{id}")
//    public String showEditParkingForm(@PathVariable int id, Model model){
//        Parking parking = parkingService.getParking(id);
//        if (parking == null) {
//            throw new NoSuchCarException("There is no parking with id = " + id);
//        }
//        model.addAttribute("parking", parking);
//        return "editParking";
//    }
//
//    @PostMapping("/parkings/update")
//    public String updateParking(@ModelAttribute Parking parking){
//        parkingService.saveParking(parking);
//        return "redirect:/parking/parkings";
//    }

    @GetMapping("/parkings/delete/{id}")
    public String deleteParking(@PathVariable int id, Model model){
        Parking parking = parkingService.getParking(id);
        if(parking == null)
        {
            throw new NoSuchCarException("There is no parking with id = " + id);
        }
        parkingService.deleteParking(id);
        model.addAttribute("message", "Parking with id = " + id + " was deleted");
        return "parkingDeleted";
    }
}
