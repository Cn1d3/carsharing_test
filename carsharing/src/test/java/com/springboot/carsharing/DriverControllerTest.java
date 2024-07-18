package com.springboot.carsharing;

import com.springboot.carsharing.controller.ImageController;
import com.springboot.carsharing.controller.ParkingController;
import com.springboot.carsharing.controller.RentController;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.service.CarService;
import com.springboot.carsharing.service.DriverService;
import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.Mockito.doNothing;

@WebMvcTest
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @MockBean
    private CarService carService;

    @MockBean
    private ImageController imageController;

    @MockBean
    private ParkingController parkingController;

    @MockBean
    private RentController rentController;

    @Test
    public void testCreateNewDriver() throws Exception {
        // Given
        Driver driver = new Driver(); // Create your driver object
        String expectedViewName = "logInPage";

//        // Mocking the service method
//        doNothing().when(driverService).registerUser(any(Driver.class));

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .flashAttr("driver", driver))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Assuming it returns HttpStatus.OK
                .andExpect(MockMvcResultMatchers.view().name(expectedViewName));
    }
}
