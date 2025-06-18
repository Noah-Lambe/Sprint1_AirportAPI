package com.keyin.airportapi.cityTests;

import com.keyin.airportapi.city.CityController;
import com.keyin.airportapi.city.CityService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityService;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean; //Injects a fake service to control what the controller receives
import org.springframework.test.web.servlet.MockMvc; //fake HTTP requests
import com.fasterxml.jackson.databind.ObjectMapper; //Converts Java <> JSON

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Test
    public void testGetCityById_ReturnsCity() throws Exception {
        City mockCity = new City();
        mockCity.setId(1L);
        mockCity.setName("St. John's");
        mockCity.setState("NL");
        mockCity.setPopulation(100000);
        mockCity.setAirports(Collections.emptyList());
        mockCity.setPassengers(Collections.emptyList());

        Mockito.when(cityService.getCityById(1L)).thenReturn(mockCity);

        mockMvc.perform(get("/city/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("St. John's"))
                .andExpect(jsonPath("$.state").value("NL"))
                .andExpect(jsonPath("$.population").value(100000));
    }


}

