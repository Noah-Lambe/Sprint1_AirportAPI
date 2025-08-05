package com.keyin.airportapi.airlineTests;

import com.keyin.airportapi.airline.AirlineController;
import com.keyin.airportapi.airline.AirlineService;
import com.keyin.airportapi.airline.Airline;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import java.util.List;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AirlineController.class)
public class AirlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AirlineService airlineService;

    // Helper to create an Airline instance
    private Airline createTestAirline(Long id, String name) {
        Airline airline = new Airline();
        if (id != null) {
            airline.setAirlineId(id);
        }
        airline.setAirlineName(name);
        return airline;
    }

    @Nested
    @DisplayName("GET Operations")
    class GetOperations {
        @Test
        @DisplayName("GET /airlines returns list of airlines")
        void testGetAllAirlines_ReturnsList() throws Exception {
            Airline a1 = createTestAirline(1L, "Air Canada");
            Airline a2 = createTestAirline(2L, "WestJet");
            Mockito.when(airlineService.getAllAirlines())
                    .thenReturn(List.of(a1, a2));

            mockMvc.perform(get("/airlines"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].airlineName").value("Air Canada"))
                    .andExpect(jsonPath("$[1].airlineName").value("WestJet"));
        }

        @Test
        @DisplayName("GET /airlines/{id} returns airline by ID")
        void testGetAirlineById() throws Exception {
            Airline a = createTestAirline(1L, "Air Canada");
            Mockito.when(airlineService.getAirlineById(1L))
                    .thenReturn(a);

            mockMvc.perform(get("/airlines/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.airlineId").value(1))
                    .andExpect(jsonPath("$.airlineName").value("Air Canada"));
        }

        @Test
        @DisplayName("GET /airlines/{id} returns 404 when service returns null")
        void testGetAirlineById_NotFound() throws Exception {
            Mockito.when(airlineService.getAirlineById(99L))
                    .thenReturn(null);

            mockMvc.perform(get("/airlines/99"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("GET /airlines/name/{name} returns airlines by name")
        void testGetAirlinesByName() throws Exception {
            Airline a = createTestAirline(1L, "Delta");
            Mockito.when(airlineService.getAirlinesByName("Delta"))
                    .thenReturn(List.of(a));

            mockMvc.perform(get("/airlines/name/Delta"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].airlineName").value("Delta"));
        }
    }

    @Nested
    @DisplayName("POST Operations")
    class PostOperations {
        @Test
        @DisplayName("POST /airlines creates a new airline")
        void testCreateAirline() throws Exception {
            Airline newAirline = createTestAirline(null, "KLM");
            Airline savedAirline = createTestAirline(5L, "KLM");
            Mockito.when(airlineService.createAirline(Mockito.any(Airline.class)))
                    .thenReturn(savedAirline);

            mockMvc.perform(post("/airlines")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newAirline)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.airlineId").value(5))
                    .andExpect(jsonPath("$.airlineName").value("KLM"));
        }
    }

    @Nested
    @DisplayName("PUT Operations")
    class PutOperations {
        @Test
        @DisplayName("PUT /airlines/{id} updates an existing airline")
        void testUpdateAirline() throws Exception {
            Airline updated = createTestAirline(1L, "NewName");
            Mockito.when(airlineService.updateAirline(Mockito.eq(1L), Mockito.any(Airline.class)))
                    .thenReturn(updated);

            mockMvc.perform(put("/airlines/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.airlineName").value("NewName"));
        }

        @Test
        @DisplayName("PUT /airlines/{id} returns 404 for non-existing airline")
        void testUpdateAirline_NotFound() throws Exception {
            Airline updated = createTestAirline(null, "NoExist");
            Mockito.when(airlineService.updateAirline(Mockito.eq(99L), Mockito.any(Airline.class)))
                    .thenReturn(null);

            mockMvc.perform(put("/airlines/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE Operations")
    class DeleteOperations {
        @Test
        @DisplayName("DELETE /airlines/{id} deletes an airline")
        void testDeleteAirline() throws Exception {
            Mockito.doNothing().when(airlineService).deleteAirline(1L);

            mockMvc.perform(delete("/airlines/1"))
                    .andExpect(status().isNoContent());

            Mockito.verify(airlineService, Mockito.times(1)).deleteAirline(1L);
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {
        @Test
        @DisplayName("GET /airlines/{id} returns 500 when service throws")
        void testGetById_InternalServerError() throws Exception {
            Mockito.when(airlineService.getAirlineById(1L))
                    .thenThrow(new RuntimeException("Service error"));

            mockMvc.perform(get("/airlines/1"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("POST /airlines returns 500 when service throws")
        void testCreate_InternalServerError() throws Exception {
            Airline newAirline = createTestAirline(null, "FailAir");
            Mockito.when(airlineService.createAirline(Mockito.any(Airline.class)))
                    .thenThrow(new RuntimeException("Service error"));

            mockMvc.perform(post("/airlines")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newAirline)))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("PUT /airlines/{id} returns 500 when service throws")
        void testUpdate_InternalServerError() throws Exception {
            Airline updated = createTestAirline(null, "Fail");
            Mockito.when(airlineService.updateAirline(Mockito.eq(1L), Mockito.any(Airline.class)))
                    .thenThrow(new RuntimeException("Service error"));

            mockMvc.perform(put("/airlines/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("DELETE /airlines/{id} returns 500 when service throws")
        void testDelete_InternalServerError() throws Exception {
            Mockito.doThrow(new RuntimeException("Service error"))
                    .when(airlineService).deleteAirline(1L);

            mockMvc.perform(delete("/airlines/1"))
                    .andExpect(status().isInternalServerError());
        }
    }
}

