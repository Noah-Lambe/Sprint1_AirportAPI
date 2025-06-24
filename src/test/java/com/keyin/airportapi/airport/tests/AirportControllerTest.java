package com.keyin.airportapi.airport.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportController;
import com.keyin.airportapi.airport.AirportService;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityRepository cityRepository;

    @MockBean
    private AirportRepository airportRepository;

    @MockBean
    private AirportService airportService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private City createCity() {
        City city = new City();
        city.setId(1L);
        city.setName("Testville");
        city.setState("Testland");
        city.setPopulation(100000L);
        return city;
    }

    @Test
    public void testGetAllAirports() throws Exception {
        City city = createCity();

        Airport airport1 = new Airport(1L, "Toronto Pearson", "YYZ", city);
        Airport airport2 = new Airport(2L, "John F Kennedy", "JFK", city);

        Mockito.when(airportService.getAllAirports()).thenReturn(Arrays.asList(airport1, airport2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].airportId").value(1))
                .andExpect(jsonPath("$[0].airportName").value("Toronto Pearson"))
                .andExpect(jsonPath("$[0].areaCode").value("YYZ"))
                .andExpect(jsonPath("$[0].city.name").value("Testville"));
    }

    @Test
    public void testGetAirportById_Found() throws Exception {
        City city = createCity();
        Airport airport = new Airport(1L, "Toronto Pearson", "YYZ", city);

        Mockito.when(airportService.getAirportById(1L)).thenReturn(Optional.of(airport));

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airportId").value(1L))
                .andExpect(jsonPath("$.airportName").value("Toronto Pearson"))
                .andExpect(jsonPath("$.areaCode").value("YYZ"))
                .andExpect(jsonPath("$.city.name").value("Testville"));
    }

    @Test
    public void testGetAirportById_NotFound() throws Exception {
        Mockito.when(airportService.getAirportById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testCreateAirport() throws Exception {
        City city = createCity();
        Airport airport = new Airport(3L, "Heathrow", "LHR", city);

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        Mockito.when(airportService.createAirport(any(Airport.class), eq(1L))).thenReturn(airport);

        mockMvc.perform(post("/")
                        .param("cityId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.airportId").value(3L))
                .andExpect(jsonPath("$.airportName").value("Heathrow"))
                .andExpect(jsonPath("$.areaCode").value("LHR"))
                .andExpect(jsonPath("$.city.name").value("Testville"));
    }

    @Test
    public void testUpdateAirport() throws Exception {
        City city = createCity();
        Airport airport = new Airport(3L, "Heathrow", "LHR", city);

        Mockito.when(airportService.updateAirport(eq(3L), any(Airport.class))).thenReturn(airport);

        mockMvc.perform(put("/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airportId").value(3L))
                .andExpect(jsonPath("$.airportName").value("Heathrow"))
                .andExpect(jsonPath("$.areaCode").value("LHR"))
                .andExpect(jsonPath("$.city.name").value("Testville"));
    }

    @Test
    public void testDeleteAirport() throws Exception {
        Mockito.doNothing().when(airportRepository).deleteById(4L);

        mockMvc.perform(delete("/4"))
                .andExpect(status().isOk());
    }
}
