package com.keyin.airportapi.cityTests;

import com.keyin.airportapi.city.CityController;
import com.keyin.airportapi.city.CityService;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    private City createTestCity(Long id, String name, String state, int population) {
        City city = new City();
        if (id != null) {
            city.setId(id); // Only call setter if id is not null
        }
        city.setName(name);
        city.setState(state);
        city.setPopulation(population);
        city.setAirports(Collections.emptyList());
        city.setPassengers(Collections.emptyList());
        return city;
    }


    @Nested
    @DisplayName("GET Operations")
    class GetOperations {
        @Test
        @DisplayName("GET /city returns list of cities")
        public void testGetAllCities_ReturnsCityList() throws Exception {
            City city1 = createTestCity(1L, "St. John's", "NL", 100000);
            City city2 = createTestCity(2L, "Corner Brook", "NL", 20000);

            Mockito.when(cityService.getAllCities()).thenReturn(List.of(city1, city2));

            mockMvc.perform(get("/city"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].name").value("St. John's"))
                    .andExpect(jsonPath("$[1].name").value("Corner Brook"));
        }

        @Test
        @DisplayName("GET /city/{id} returns city by ID")
        public void testGetCityById() throws Exception {
            City mockCity = createTestCity(1L, "St. John's", "NL", 100000);

            Mockito.when(cityService.getCityById(1L)).thenReturn(mockCity);

            mockMvc.perform(get("/city/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value("St. John's"))
                    .andExpect(jsonPath("$.state").value("NL"))
                    .andExpect(jsonPath("$.population").value(100000));
        }

        @Test
        @DisplayName("GET /city/{id} returns 404 for non-existing city")
        public void testGetCityById_NotFound() throws Exception {
            long nonExistingCityId = 99L;

            Mockito.when(cityService.getCityById(nonExistingCityId)).thenReturn(null);

            mockMvc.perform(get("/city/" + nonExistingCityId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("GET /city_search returns cities by airport name")
        public void testSearchCitiesByAirportName() throws Exception {
            City city1 = new City();
            city1.setId(1L);
            city1.setName("St. John's");
            city1.setState("NL");
            city1.setPopulation(100000);

            Airport airport = new Airport();
            airport.setAirportName("St. John's International Airport");
            city1.setAirports(Collections.singletonList(airport));

            Mockito.when(cityService.getCitiesByAirportName("St. John's International Airport"))
                    .thenReturn(List.of(city1));

            mockMvc.perform(get("/city_search")
                            .param("airport_name", "St. John's International Airport"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].name").value("St. John's"));
        }

        @Test
        @DisplayName("GET /city_search returns cities by passenger phone")
        public void testSearchCitiesByPassengerPhone() throws Exception {
            City city1 = createTestCity(1L, "St. John's", "NL", 100000);
            Passenger passenger = new Passenger();
            passenger.setPhoneNumber("123-456-7890");
            city1.setPassengers(Collections.singletonList(passenger));

            Mockito.when(cityService.getCitiesByPassengerPhone("123-456-7890"))
                    .thenReturn(List.of(city1));

            mockMvc.perform(get("/city_search")
                            .param("passenger_phone", "123-456-7890"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].name").value("St. John's"));
        }

        @Test
        @DisplayName("GET /city_search returns empty list when no search parameters provided")
        public void testSearchCities_NoParameters() throws Exception {
            mockMvc.perform(get("/city_search"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(0));
        }

        @Test
        @DisplayName("GET /city_search returns empty list when no matching cities found")
        public void testSearchCities_NoMatches() throws Exception {
            Mockito.when(cityService.getCitiesByAirportName(Mockito.anyString())).thenReturn(Collections.emptyList());
            Mockito.when(cityService.getCitiesByPassengerPhone(Mockito.anyString())).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/city_search")
                            .param("airport_name", "Non-Existent Airport"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(0));
        }
    }

    @Nested
    @DisplayName("POST Operations")
    class PostOperations {
        @Test
        @DisplayName("POST /city creates a new city")
        public void testCreateCity() throws Exception {
            City newCity = createTestCity(null, "St. John's", "NL", 100000);

            City savedCity = createTestCity(1L, "St. John's", "NL", 100000);

            Mockito.when(cityService.createCity(Mockito.any(City.class))).thenReturn(savedCity);

            mockMvc.perform(post("/city")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newCity)))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.name").value("St. John's"));
        }
    }

        @Nested
        @DisplayName("PUT Operations")
        class PutOperations {
            @Test
            @DisplayName("PUT /city/{id} updates an existing city")
            public void testUpdateCity() throws Exception {
                City updatedCity = createTestCity(1L, "St. John's", "NL", 120000);

                Mockito.when(cityService.updateCity(Mockito.eq(1L), Mockito.any(City.class))).thenReturn(updatedCity);

                mockMvc.perform(put("/city/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedCity)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("St. John's"))
                        .andExpect(jsonPath("$.population").value(120000));
            }

            @Test
            @DisplayName("PUT /city/{id} returns 404 for non-existing city")
            public void testUpdateCity_NotFound() throws Exception {
                long nonExistingCityId = 99L;
                City updatedCity = createTestCity(nonExistingCityId, "St. John's", "NL", 120000);

                Mockito.when(cityService.updateCity(Mockito.eq(nonExistingCityId), Mockito.any(City.class))).thenReturn(null);

                mockMvc.perform(put("/city/" + nonExistingCityId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedCity)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("DELETE Operations")
        class DeleteOperations {
            @Test
            @DisplayName("DELETE /city/{id} deletes a city")
            public void testDeleteCityById() throws Exception {
                long cityId = 1L;

                Mockito.doNothing().when(cityService).deleteCityById(cityId);

                mockMvc.perform(delete("/city/" + cityId))
                        .andExpect(status().isNoContent());

                Mockito.verify(cityService, Mockito.times(1)).deleteCityById(cityId);
            }

            @Test
            @DisplayName("DELETE /city/{id} returns 404 for non-existing city")
            public void testDeleteCityById_NotFound() throws Exception {
                long nonExistingCityId = 99L;

                Mockito.doThrow(new RuntimeException("City not found")).when(cityService).deleteCityById(nonExistingCityId);

                mockMvc.perform(delete("/city/" + nonExistingCityId))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("Error Handling")
        class ErrorHandling {
            @Test
            @DisplayName("GET /city/{id} returns 500 Internal Server Error on service exception")
            public void testGetCityById_InternalServerError() throws Exception {
                long cityId = 1L;

                Mockito.when(cityService.getCityById(cityId)).thenThrow(new RuntimeException("Service error"));

                mockMvc.perform(get("/city/" + cityId))
                        .andExpect(status().isInternalServerError());
            }

            @Test
            @DisplayName("POST /city returns 500 Internal Server Error on service exception")
            public void testCreateCity_InternalServerError() throws Exception {
                City newCity = createTestCity(null, "St. John's", "NL", 100000);

                Mockito.when(cityService.createCity(Mockito.any(City.class))).thenThrow(new RuntimeException("Service error"));

                mockMvc.perform(post("/city")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newCity)))
                        .andExpect(status().isInternalServerError());
            }

            @Test
            @DisplayName("PUT /city/{id} returns 500 Internal Server Error on service exception")
            public void testUpdateCity_InternalServerError() throws Exception {
                long cityId = 1L;
                City updatedCity = createTestCity(cityId, "St. John's", "NL", 120000);

                Mockito.when(cityService.updateCity(Mockito.eq(cityId), Mockito.any(City.class)))
                        .thenThrow(new RuntimeException("Service error"));

                mockMvc.perform(put("/city/" + cityId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedCity)))
                        .andExpect(status().isInternalServerError());
            }

            @Test
            @DisplayName("DELETE /city/{id} returns 500 Internal Server Error on service exception")
            public void testDeleteCityById_InternalServerError() throws Exception {
                long cityId = 1L;

                Mockito.doThrow(new RuntimeException("Service error")).when(cityService).deleteCityById(cityId);

                mockMvc.perform(delete("/city/" + cityId))
                        .andExpect(status().isInternalServerError());
            }
        }
    }
