package com.springboot.carsharing;

import com.springboot.carsharing.controller.CarController;
import com.springboot.carsharing.controller.ImageController;
import com.springboot.carsharing.controller.ParkingController;
import com.springboot.carsharing.controller.RentController;
import com.springboot.carsharing.entity.Car;
import com.springboot.carsharing.entity.Image;
import com.springboot.carsharing.service.CarService;
import com.springboot.carsharing.service.DriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    @WebMvcTest(CarController.class)
    public class CarControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CarService carService;

        @Test
        public void testShowAllCars() throws Exception {
            // Mocking carService.getAllCars method
            List<Car> cars = new ArrayList<>();
            cars.add(new Car(
                    100,
                    "Toyota",
                    "Camry",
                    2020,
                    "ABC123",
                    "common",
                    3,
                    true,
                    "https://toyota.com.ua/360/6/4X7/33.png",
                    new ArrayList<>(),
                    0,
                    null,
                    null,
                    LocalDateTime.now()
            ));
            Mockito.when(carService.getAllCars(null, null)).thenReturn(cars);

            // Making request and verifying response
            mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attributeExists("cars"))
                    .andExpect(MockMvcResultMatchers.view().name("allCars"));
        }
    }


