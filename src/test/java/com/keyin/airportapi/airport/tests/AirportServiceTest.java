package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.airport.AirportService;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    private CityRepository cityRepository;

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
        City city = new City(2L, "Toronto", "ON", 1500000L);
        Airport airport = new Airport(2L, "TRU", "456", city);

        when(airportRepository.findById(2L)).thenReturn(Optional.of(airport));

        Optional<Airport> result = airportService.getAirportById(2L);

        assertTrue(result.isPresent());
        assertEquals("TRU", result.get().getAirportName());
        verify(airportRepository).findById(2L);
    }

    @Test
    public void testCreateAirport() {
        City city = new City();
        city.setId(1L);
        city.setName("Test City");

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(airportRepository.save(any(Airport.class))).thenAnswer(i -> i.getArgument(0));

        Airport airport = new Airport();
        airport.setAirportName("Test Airport");
        airport.setAreaCode("TST");

        Airport createdAirport = airportService.createAirport(airport, 1L);

        assertNotNull(createdAirport);
        assertEquals("Test Airport", createdAirport.getAirportName());
        assertEquals("TST", createdAirport.getAreaCode());
        assertEquals(city, createdAirport.getCity());

        verify(cityRepository).findById(1L);
        verify(airportRepository).save(any(Airport.class));
    }

    @Test
    void testUpdateAirport_WhenExists() {
        City city = new City(4L, "Halifax", "NS", 600000L);

        Airport existing = new Airport(1L, "OldName", "111", city);
        Airport updated = new Airport(1L, "NewName", "999", city);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(airportRepository.save(any())).thenReturn(updated);

        Airport result = airportService.updateAirport(1L, updated);

        assertEquals("NewName", result.getAirportName());
        assertEquals("999", result.getAreaCode());
        verify(airportRepository).save(any(Airport.class));  // changed from existing to any() or updated
    }

    @Test
    void testUpdateAirport_WhenNotExists() {
        City city = new City(5L, "Montreal", "QC", 1500000L);

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
