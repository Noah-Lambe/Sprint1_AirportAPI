package com.keyin.airportapi.passenger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PassengerControllerTest {

    private PassengerController controller;
    private PassengerService service;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(PassengerService.class);
        controller = new PassengerController();
        controller.passengerService = service; // Inject mock
    }

    @Test
    void testGetAllPassengers() {
        Passenger p = new Passenger();
        p.setFirstName("Hunter");

        when(service.getAllPassengers()).thenReturn(List.of(p));

        List<Passenger> result = controller.getAllPassengers();
        assertEquals(1, result.size());
        assertEquals("Hunter", result.get(0).getFirstName());
    }

    @Test
    void testGetPassengerById() {
        Passenger p = new Passenger();
        p.setId(1L);
        p.setFirstName("Hunter");

        when(service.getPassengerById(1L)).thenReturn(Optional.of(p));

        Passenger result = controller.getPassengerById(1L);
        assertEquals("Hunter", result.getFirstName());
    }

    @Test
    void testAddPassenger() {
        Passenger p = new Passenger();
        p.setFirstName("Hunter");

        when(service.savePassenger(p)).thenReturn(p);

        Passenger result = controller.addPassenger(p);
        assertEquals("Hunter", result.getFirstName());
    }

    @Test
    void testDeletePassenger() {
        controller.deletePassenger(1L);
        verify(service, times(1)).deletePassenger(1L);
    }

}
