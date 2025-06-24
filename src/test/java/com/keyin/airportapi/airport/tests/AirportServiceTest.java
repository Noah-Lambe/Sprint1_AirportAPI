package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.airport.AirportService;
import com.keyin.airportapi.city.City;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAirports() {
        City city1 = new City(1L, "New York", "NY", 1000000L);
        City city2 = new City(2L, "Toronto", "ON", 1500000L);

        Airport airport1 = new Airport(1L, "JFK", "123", city1);
        Airport airport2 = new Airport(2L, "TRU", "456", city2);

        when(airportRepository.findAll()).thenReturn(Arrays.asList(airport1, airport2));
        List<Airport> airports = airportService.getAllAirports();

        assertEquals(2, airports.size());
        assertEquals(airport1, airports.get(0));
        assertEquals(airport2, airports.get(1));
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    void testGetAirportById() {
        City city = new City(2L, "Toronto", "ON", 1500000);
        Airport airport = new Airport(2L, "TRU", "456", city);
        when(airportRepository.findById(2L)).thenReturn(Optional.of(airport));

        Optional<Airport> result = airportService.getAirportById(2L);

        assertTrue(result.isPresent());
        assertEquals("TRU", result.get().getAirportName());
        verify(airportRepository).findById(2L);
    }

    @Test
    void testCreateAirport() {
        City city = new City(3L, "Vancouver", "BC", 800000);
        Airport airport = new Airport(2L, "TRU", "456", city);
        when(airportRepository.save(airport)).thenReturn(airport);

        Airport result = airportService.createAirport(airport, 3L);

        assertNotNull(result);
        assertEquals("TRU", result.getAirportName());
        verify(airportRepository, times(1)).save(airport);
    }

    @Test
    void testUpdateAirport_WhenExists() {
        City city = new City(4L, "Halifax", "NS", 600000);

        Airport existing = new Airport(1L, "OldName", "111", city);
        Airport updated = new Airport(1L, "NewName", "999", city);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(airportRepository.save(any())).thenReturn(updated);

        Airport result = airportService.updateAirport(1L, updated);

        assertEquals("NewName", result.getAirportName());
        assertEquals("999", result.getAreaCode());
        verify(airportRepository).save(existing);
    }

    @Test
    void testUpdateAirport_WhenNotExists() {
        City city = new City(5L, "Montreal", "QC", 1500000);

        Airport updated = new Airport(1L, "CreatedName", "555", city);

        when(airportRepository.findById(1L)).thenReturn(Optional.empty());
        when(airportRepository.save(any())).thenReturn(updated);

        Airport result = airportService.updateAirport(1L, updated);

        assertEquals("CreatedName", result.getAirportName());
        verify(airportRepository).save(updated);
    }

    @Test
    void testDeleteAirport() {
        doNothing().when(airportRepository).deleteById(1L);

        airportService.deleteAirport(1L);

        verify(airportRepository, times(1)).deleteById(1L);
    }
}