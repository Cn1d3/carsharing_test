package com.springboot.carsharing;

import com.springboot.carsharing.controller.ParkingController;
import com.springboot.carsharing.entity.Parking;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.service.ParkingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingControllerTest {

    @Mock
    private ParkingService parkingService;

    @InjectMocks
    private ParkingController parkingController;

    @Mock
    private Model model;

    @Test
    public void testShowAllParkings() {
        List<Parking> parkingList = new ArrayList<>();
        parkingList.add(new Parking(1, "Address 1", 49.97421951906027F, 36.29831747360842F, new ArrayList<>()));
        parkingList.add(new Parking(2, "Address 2", 49.97421951906027F, 36.29831747360842F, new ArrayList<>()));
        when(parkingService.showAllParkings()).thenReturn(parkingList);

        String viewName = parkingController.showAllParkings(model);

        assertEquals("allParkings", viewName);
        verify(model).addAttribute("parkings", parkingList);
    }

    @Test
    public void testGetParking() {
        int parkingId = 1;
        Parking parking = new Parking(1, "Address 1", 49.97421951906027F, 36.29831747360842F, new ArrayList<>());
        when(parkingService.getParking(parkingId)).thenReturn(parking);

        String viewName = parkingController.getParking(parkingId, model);

        assertEquals("parkingDetails", viewName);
        verify(model).addAttribute("parking", parking);
    }

    @Test
    public void testDeleteParking() {
        int parkingId = 1;
        Parking parking = new Parking(1, "Address 1", 49.97421951906027F, 36.29831747360842F, new ArrayList<>());
        when(parkingService.getParking(parkingId)).thenReturn(parking);

        String viewName = parkingController.deleteParking(parkingId, model);

        assertEquals("parkingDeleted", viewName);
        verify(model).addAttribute("message", "Parking with id = " + parkingId + " was deleted");
        verify(parkingService).deleteParking(parkingId);
    }

    @Test
    public void testGetParking_ThrowsNoSuchCarException() {
        int parkingId = 1;
        when(parkingService.getParking(parkingId)).thenReturn(null);

        assertThrows(NoSuchCarException.class, () -> parkingController.getParking(parkingId, model));
    }

    @Test
    public void testDeleteParking_ThrowsNoSuchCarException() {
        int parkingId = 1;
        when(parkingService.getParking(parkingId)).thenReturn(null);

        assertThrows(NoSuchCarException.class, () -> parkingController.deleteParking(parkingId, model));
    }
}

