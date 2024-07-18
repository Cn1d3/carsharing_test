package com.springboot.carsharing.controller;

import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegController {

    @Autowired
    private DriverService userService;

    @GetMapping("/form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Driver());
        return "regPage";
    }

//    @PostMapping("/process")
//    public String processRegistrationForm(@ModelAttribute Driver driver) {
//        userService.registerUser(driver);
//        return "redirect:/registration/success"; // Redirect to a success page
//    }

    @GetMapping("/success")
    public String registrationSuccess() {
        return "registrationSuccess"; // Assuming you have a "registrationSuccess" view.
    }
}