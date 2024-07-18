package com.springboot.carsharing.controller;

import com.springboot.carsharing.configs.MyUserDetails;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
@Slf4j
@Controller
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/login")
    public String login(){
        return "logInPage";
    }

    @GetMapping("/registration")
    public String registration(){
        return "regPage";
    }

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getAvatar(@AuthenticationPrincipal MyUserDetails details){
        if(details.getDriver().getAvatar()==null){
            log.error("Image not found");
            return ResponseEntity.badRequest().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", details.getDriver().getAvatar().getOriginalFileName());
        headers.setContentLength(details.getDriver().getAvatar().getBytes().length);
        return ResponseEntity.ok().headers(headers).body(details.getDriver().getAvatar().getBytes());
    }

    @PostMapping("/registration")
    public String createNewDriver(Driver driver, Model model, @RequestParam(name = "file1", required = false) MultipartFile file1) throws IOException {
        for(Driver driver1 : driverService.getAllDrivers(null)){
            if(driver.getEmail().equals(driver1.getEmail())){
                model.addAttribute("errorMessage", "Email has already been registered.");
                return "regPage";
            }
        }
        driverService.registerUser(driver, file1);
        model.addAttribute(driver);
        return "logInPage";
    }

    @GetMapping("/drivers")
    public String showAllDrivers(@RequestParam(name = "email", required = false) String email, Model model){
        List<Driver> allDrivers = driverService.getAllDrivers(email);
        model.addAttribute("drivers", allDrivers);
        return "allDrivers";
    }

    @GetMapping("/drivers/profile/{id}")
    public String getDriverById(@PathVariable int id, Model model){
        Driver driver = driverService.getDriver(id);
        if (driver == null) {
            throw new NoSuchCarException("There is no driver with id = " + id);
        }
        model.addAttribute("driver", driver);
        return "driverInfo";
    }

    @GetMapping("/drivers/profile")
    public String getDriver(@AuthenticationPrincipal MyUserDetails details, Model model){
        if (details == null) {
            return "preLogin";
        }
        model.addAttribute("driver", details.getDriver());
        return "profilePage";
    }

//    @GetMapping("/drivers/edit/{id}")
//    public String showEditDriverForm(@PathVariable int id, Model model){
//        Driver driver = driverService.getDriver(id);
//        if (driver == null) {
//            throw new NoSuchCarException("There is no driver with id = " + id);
//        }
//        model.addAttribute("driver", driver);
//        return "editDriver"; // Assuming you have an "editDriver" view.
//    }

    @PostMapping("/drivers/update")
    public String updateDriver(@ModelAttribute Driver driver){
        driverService.saveDriver(driver);
        return "redirect:/driver/drivers";
    }

    @PostMapping("/drivers/delete/{id}")
    public String deleteDriver(@PathVariable int id, Model model){
        Driver driver = driverService.getDriver(id);
        if(driver == null)
        {
            throw new NoSuchCarException("There is no driver with id = " + id);
        }
        driverService.deleteDriver(id);
        model.addAttribute("message", "Driver with id = " + id + " was deleted");
        return "redirect:/drivers";
    }
}
