package com.keyin.airportapi.aircrafttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.aircraft.AircraftController;
import com.keyin.airportapi.aircraft.AircraftService;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AircraftController.class)
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AircraftService aircraftService;

    private Aircraft createTestAircraft(Long id, String type, String airline, int numPassengers) {
        Aircraft a = new Aircraft(type, airline, numPassengers);
        if (id != null) a.setType(type);
        return a;
    }

    @Nested
    @DisplayName("GET Operations")
    class GetOps {
        @Test
        void testGetAllAircraft() throws Exception {
            Aircraft a1 = createTestAircraft(1L, "Boeing 737", "Air Canada", 150);
            Aircraft a2 = createTestAircraft(2L, "Airbus A320", "WestJet", 180);
            Mockito.when(aircraftService.getAllAircraft()).thenReturn(List.of(a1, a2));

            mockMvc.perform(get("/aircraft"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2));
        }

        @Test
        void testGetAircraftById() throws Exception {
            Aircraft aircraft = createTestAircraft(1L, "Boeing 737", "Air Canada", 150);
            Mockito.when(aircraftService.getAircraftById(1L)).thenReturn(Optional.of(aircraft));

            mockMvc.perform(get("/aircraft/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value("Boeing 737"));
        }

        @Test
        void testGetAircraftById_NotFound() throws Exception {
            Mockito.when(aircraftService.getAircraftById(99L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/aircraft/99"))
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("POST Operations")
    class PostOps {
        @Test
        void testCreateAircraft() throws Exception {
            Aircraft input = createTestAircraft(null, "Boeing 737", "Air Canada", 150);
            Aircraft saved = createTestAircraft(1L, "Boeing 737", "Air Canada", 150);
            Mockito.when(aircraftService.createAircraft(Mockito.any())).thenReturn(saved);

            mockMvc.perform(post("/aircraft")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value("Boeing 737"));
        }
    }

    @Nested
    @DisplayName("PUT Operations")
    class PutOps {
        @Test
        void testUpdateAircraft() throws Exception {
            Aircraft updated = createTestAircraft(1L, "Airbus A320", "WestJet", 180);
            Mockito.when(aircraftService.updateAircraft(Mockito.eq(1L), Mockito.any())).thenReturn(updated);

            mockMvc.perform(put("/aircraft/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value("Airbus A320"));
        }
    }

    @Nested
    @DisplayName("DELETE Operations")
    class DeleteOps {
        @Test
        void testDeleteAircraft() throws Exception {
            mockMvc.perform(delete("/aircraft/1"))
                    .andExpect(status().isOk());
            Mockito.verify(aircraftService, Mockito.times(1)).deleteAircraft(1L);
        }
    }
}