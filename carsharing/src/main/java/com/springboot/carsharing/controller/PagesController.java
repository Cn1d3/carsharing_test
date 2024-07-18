package com.springboot.carsharing.controller;

import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.entity.Rent;
import com.springboot.carsharing.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PagesController {
    @Autowired
    DriverService driverService;

    @GetMapping("/")
    public String getMainPage() {
        return "main";
    }

    @PostMapping("/drivers/{id}")
    public String profile(@PathVariable int id, Model model) {
        Driver driver = driverService.getDriver(id);
        model.addAttribute("driver", driver);
        return "profilePage";
    }
}