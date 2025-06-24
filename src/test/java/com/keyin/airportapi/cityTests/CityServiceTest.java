package com.keyin.airportapi.cityTests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.city.CityService;
import com.keyin.airportapi.passenger.Passenger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    @DisplayName("Should return all cities from repository")
    public void testGetAllCities() {
        City mockCity1 = new City();
        mockCity1.setId(1L);
        mockCity1.setName("St. John's");
        mockCity1.setState("NL");
        mockCity1.setPopulation(100000);
        mockCity1.setAirports(Collections.emptyList());
        mockCity1.setPassengers(Collections.emptyList());

        City mockCity2 = new City();
        mockCity2.setId(2L);
        mockCity2.setName("CBS");
        mockCity2.setState("NL");
        mockCity2.setPopulation(35000);
        mockCity2.setAirports(Collections.emptyList());
        mockCity2.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.findAll()).thenReturn(List.of(mockCity1, mockCity2));

        List<City> result = cityService.getAllCities(); // method call

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("St. John's", result.get(0).getName());
        assertEquals("CBS", result.get(1).getName());
    }

    @Test
    @DisplayName("Should return city by ID when city exists")
    public void testGetCityById() {
        City mockCity = new City();
        mockCity.setId(1L);
        mockCity.setName("St. John's");
        mockCity.setState("NL");
        mockCity.setPopulation(100000);
        mockCity.setAirports(Collections.emptyList());
        mockCity.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(mockCity));

        City result = cityService.getCityById(1L); // method call

        assertNotNull(result);
        assertEquals("St. John's", result.getName());
        assertEquals("NL", result.getState());
    }

    @Test
    @DisplayName("Should return null when city not found by ID")
    public void testGetCityById_NotFound() {
        Mockito.when(cityRepository.findById(99L)).thenReturn(Optional.empty());

        City result = cityService.getCityById(99L); // method call

        assertNull(result);
    }

    @Test
    @DisplayName("Should return cities matching airport name")
    public void testGetCitiesByAirportName() {
        City mockCity = new City();
        mockCity.setId(1L);
        mockCity.setName("St. John's");
        mockCity.setState("NL");
        mockCity.setPopulation(100000);
        Airport airport = new Airport();
        airport.setAirportName("St. John's International Airport");
        mockCity.setAirports(List.of(airport));
        mockCity.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.findByAirports_Name("St. John's International Airport"))
                .thenReturn(List.of(mockCity));

        List<City> result = cityService.getCitiesByAirportName("St. John's International Airport"); // method call

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("St. John's", result.get(0).getName());
    }

    @Test
    @DisplayName("Should return cities matching passenger phone number")
    public void testGetCitiesByPassengerPhone() {
        City mockCity = new City();
        mockCity.setId(1L);
        mockCity.setName("St. John's");
        mockCity.setState("NL");
        mockCity.setPopulation(100000);
        Passenger passenger = new Passenger();
        passenger.setPhoneNumber("123-456-7890");
        mockCity.setPassengers(List.of(passenger));
        mockCity.setAirports(Collections.emptyList());

        Mockito.when(cityRepository.findByPassengers_Phone("123-456-7890"))
                .thenReturn(List.of(mockCity));

        List<City> result = cityService.getCitiesByPassengerPhone("123-456-7890"); // method call

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("St. John's", result.get(0).getName());
    }

    @Test
    @DisplayName("Should call deleteById on repository")
    public void testDeleteCityById() {
        long cityId = 1L;

        cityService.deleteCityById(cityId); // method call

        Mockito.verify(cityRepository, Mockito.times(1)).deleteById(cityId);
    }

    @Test
    @DisplayName("Should update city when city exists")
    public void testUpdateCity() {
        City existingCity = new City();
        existingCity.setId(1L);
        existingCity.setName("Old Name");
        existingCity.setState("Old State");
        existingCity.setPopulation(50000);
        existingCity.setAirports(Collections.emptyList());
        existingCity.setPassengers(Collections.emptyList());

        City updatedCity = new City();
        updatedCity.setName("New Name");
        updatedCity.setState("New State");
        updatedCity.setPopulation(60000);
        updatedCity.setAirports(Collections.emptyList());
        updatedCity.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(existingCity));
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenReturn(updatedCity);

        City result = cityService.updateCity(1L, updatedCity); // method call

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("New State", result.getState());
        assertEquals(60000, result.getPopulation());
    }

    @Test
    @DisplayName("Should return null when updating non-existent city")
    public void testUpdateCity_NotFound() {
        City updatedCity = new City();
        updatedCity.setName("New Name");
        updatedCity.setState("New State");
        updatedCity.setPopulation(60000);
        updatedCity.setAirports(Collections.emptyList());
        updatedCity.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.findById(99L)).thenReturn(Optional.empty());

        City result = cityService.updateCity(99L, updatedCity); // method call

        assertNull(result);
    }

    @Test
    @DisplayName("Should create and return a new city")
    public void testCreateCity() {
        City newCity = new City();
        newCity.setName("Gander");
        newCity.setState("NL");
        newCity.setPopulation(12000);
        newCity.setAirports(Collections.emptyList());
        newCity.setPassengers(Collections.emptyList());

        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenReturn(newCity);

        City result = cityService.createCity(newCity); // method call

        assertNotNull(result);
        assertEquals("Gander", result.getName());
    }
}
