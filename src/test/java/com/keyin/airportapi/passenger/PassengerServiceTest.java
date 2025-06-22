package com.keyin.airportapi.passenger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PassengerServiceTest {

    private PassengerRepository passengerRepo;
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        passengerRepo = Mockito.mock(PassengerRepository.class);
        passengerService = new PassengerService();
        passengerService.passengerRepo = passengerRepo; // Inject mock
    }

    @Test
    void testGetAllPassengers() {
        Passenger p1 = new Passenger();
        p1.setFirstName("Hunter");
        p1.setLastName("Saunders");

        when(passengerRepo.findAll()).thenReturn(List.of(p1));

        List<Passenger> result = passengerService.getAllPassengers();
        assertEquals(1, result.size());
        assertEquals("Hunter", result.get(0).getFirstName());
    }

    @Test
    void testGetPassengerByIdFound() {
        Passenger p = new Passenger();
        p.setId(1L);
        p.setFirstName("Hunter");

        when(passengerRepo.findById(1L)).thenReturn(Optional.of(p));

        Optional<Passenger> result = passengerService.getPassengerById(1L);
        assertTrue(result.isPresent());
        assertEquals("Hunter", result.get().getFirstName());
    }

    @Test
    void testSavePassenger() {
        Passenger p = new Passenger();
        p.setFirstName("Hunter");

        when(passengerRepo.save(p)).thenReturn(p);

        Passenger result = passengerService.savePassenger(p);
        assertEquals("Hunter", result.getFirstName());
    }

    @Test
    void testDeletePassenger() {
        passengerService.deletePassenger(1L);
        verify(passengerRepo, times(1)).deleteById(1L);
    }

}
