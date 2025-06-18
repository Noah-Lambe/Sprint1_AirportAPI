package com.keyin.airportapi.cityTests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.city.CityService;
import com.keyin.airportapi.passenger.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testGetCityById_ReturnsCity() {
        City mockCity = new City();
        mockCity.setId(1L);
        mockCity.setName("St. John's");
        mockCity.setState("NL");
        mockCity.setPopulation(100000);
        mockCity.setAirports(Collections.emptyList());
        mockCity.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(mockCity));

        City result = cityService.getCityById(1L); // method test

        assertNotNull(result);
        assertEquals("St. John's", result.getName());
        assertEquals("NL", result.getState());
    }

    @Test
    public void testGetCityById_NotFound_ReturnsNull() {
        Mockito.when(cityRepository.findById(99L)).thenReturn(Optional.empty());

        City result = cityService.getCityById(99L);

        assertNull(result);
    }
}

