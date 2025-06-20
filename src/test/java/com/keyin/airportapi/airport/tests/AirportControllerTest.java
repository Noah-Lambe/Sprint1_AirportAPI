package com.keyin.airportapi.airport.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.airport.AirportService;
import com.keyin.airportapi.airport.AirportController;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportRepository airportRepository;

    @MockBean
    private AirportService airportService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllAirports() throws Exception {
        Airport airport1 = new Airport(1, "Toronto Pearson", "YYZ");
        Airport airport2 = new Airport(2, "John F Kennedy", "JFK");

        Mockito.when(airportService.getAllAirports()).thenReturn(Arrays.asList(airport1, airport2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].airportId").value(1))
                .andExpect(jsonPath("$[0].airportName").value("Toronto Pearson"))
                .andExpect(jsonPath("$[0].areaCode").value("YYZ"));
    }

    @Test
    public void testGetAirportById_Found() throws Exception {
        Airport airport = new Airport(1, "Toronto Pearson", "YYZ");

        Mockito.when(airportRepository.findById(1)).thenReturn(Optional.of(airport));

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airportId").value(1))
                .andExpect(jsonPath("$.airportName").value("Toronto Pearson"))
                .andExpect(jsonPath("$.areaCode").value("YYZ"));
    }

    @Test
    public void testGetAirportById_NotFound() throws Exception {
        Mockito.when(airportRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAirport() throws Exception {
        Airport airport = new Airport(3, "Heathrow", "LHR");

        Mockito.when(airportRepository.save(any(Airport.class))).thenReturn(airport);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.airportId").value(3))
                .andExpect(jsonPath("$.airportName").value("Heathrow"))
                .andExpect(jsonPath("$.areaCode").value("LHR"));
    }

    @Test
    public void testUpdateAirport() throws Exception {
        Airport airport = new Airport(3, "Heathrow", "LHR");

        Mockito.when(airportRepository.save(any(Airport.class))).thenReturn(airport);

        mockMvc.perform(put("/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airportId").value(3))
                .andExpect(jsonPath("$.airportName").value("Heathrow"))
                .andExpect(jsonPath("$.areaCode").value("LHR"));
    }

    @Test
    public void testDeleteAirport() throws Exception {
        Airport airport = new Airport(4, "Berlin Brandenburg", "BER");

        Mockito.doNothing().when(airportRepository).delete(any(Airport.class));

        mockMvc.perform(delete("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isOk());
    }
}
