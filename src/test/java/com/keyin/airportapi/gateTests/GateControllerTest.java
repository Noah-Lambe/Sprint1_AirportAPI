package com.keyin.airportapi.gateTests;

import com.keyin.airportapi.gate.GateController;
import com.keyin.airportapi.gate.GateService;
import com.keyin.airportapi.gate.Gate;
import com.keyin.airportapi.airport.Airport;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GateController.class)
public class GateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GateService gateService;

    private Gate createTestGate(Long id, String terminal, String gateName, Long airportId) {
        Gate gate = new Gate();
        if (id != null) gate.setGateId(id);
        gate.setTerminal(terminal);
        gate.setGateName(gateName);
        if (airportId != null) {
            Airport a = new Airport();
            a.setAirportId(airportId);
            gate.setAirport(a);
        }
        return gate;
    }

    @Nested
    @DisplayName("GET Operations")
    class GetOperations {
        @Test
        @DisplayName("GET /gates returns list of gates")
        void testGetAllGates() throws Exception {
            Gate g1 = createTestGate(1L, "T1", "G1", 10L);
            Gate g2 = createTestGate(2L, "T2", "G2", 20L);
            Mockito.when(gateService.getAllGates()).thenReturn(List.of(g1, g2));

            mockMvc.perform(get("/gates"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].terminal").value("T1"))
                    .andExpect(jsonPath("$[1].gateName").value("G2"));
        }

        @Test
        @DisplayName("GET /gates/{id} returns gate by ID")
        void testGetGateById_Found() throws Exception {
            Gate g = createTestGate(5L, "X", "G5", 15L);
            Mockito.when(gateService.getGateById(5L)).thenReturn(g);

            mockMvc.perform(get("/gates/5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.gateId").value(5))
                    .andExpect(jsonPath("$.gateName").value("G5"));
        }

        @Test
        @DisplayName("GET /gates/{id} returns 500 on service error")
        void testGetGateById_Error() throws Exception {
            Mockito.when(gateService.getGateById(99L))
                    .thenThrow(new RuntimeException("oops"));

            mockMvc.perform(get("/gates/99"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("GET /gates/airport/{airportId} filters by airport and returns gateName")
        void testGetGatesByAirportId() throws Exception {
            Gate g = createTestGate(3L, "A", "G3", 7L);
            Mockito.when(gateService.getGatesByAirportId(7L)).thenReturn(List.of(g));

            mockMvc.perform(get("/gates/airport/7"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].gateName").value("G3"));  // now asserting gateName
        }

        @Test
        @DisplayName("GET /gates/terminal/{terminal} filters by terminal")
        void testGetGatesByTerminal() throws Exception {
            Gate g = createTestGate(4L, "B", "G4", 8L);
            Mockito.when(gateService.getGatesByTerminal("B")).thenReturn(List.of(g));

            mockMvc.perform(get("/gates/terminal/B"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].terminal").value("B"));
        }

        @Test
        @DisplayName("GET /gates/name/{gateName} filters by gate name")
        void testGetGatesByGateName() throws Exception {
            Gate g = createTestGate(6L, "C", "Z9", 12L);
            Mockito.when(gateService.getGatesByGateName("Z9")).thenReturn(List.of(g));

            mockMvc.perform(get("/gates/name/Z9"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].gateName").value("Z9"));
        }
    }

    @Nested
    @DisplayName("POST Operations")
    class PostOperations {
        @Test
        @DisplayName("POST /gates creates a new gate")
        void testCreateGate_Success() throws Exception {
            Gate in = createTestGate(null, "T3", "G3", 30L);
            Gate out = createTestGate(9L, "T3", "G3", 30L);
            Mockito.when(gateService.createGate(Mockito.any(Gate.class))).thenReturn(out);

            mockMvc.perform(post("/gates")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(in)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.gateId").value(9))
                    .andExpect(jsonPath("$.terminal").value("T3"));
        }

        @Test
        @DisplayName("POST /gates returns 500 on service error")
        void testCreateGate_Error() throws Exception {
            Gate in = createTestGate(null, "T4", "G4", 40L);
            Mockito.when(gateService.createGate(Mockito.any(Gate.class)))
                    .thenThrow(new RuntimeException("fail"));

            mockMvc.perform(post("/gates")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(in)))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("PUT Operations")
    class PutOperations {
        @Test
        @DisplayName("PUT /gates/{id} updates an existing gate")
        void testUpdateGate() throws Exception {
            Gate in = createTestGate(null, "T9", "G9", 90L);
            Gate out = createTestGate(2L, "T9", "G9", 90L);
            Mockito.when(gateService.updateGate(Mockito.eq(2L), Mockito.any(Gate.class)))
                    .thenReturn(out);

            mockMvc.perform(put("/gates/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(in)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.gateId").value(2))
                    .andExpect(jsonPath("$.gateName").value("G9"));
        }

        @Test
        @DisplayName("PUT /gates/{id} returns 404 when not found")
        void testUpdateGate_NotFound() throws Exception {
            Mockito.when(gateService.updateGate(Mockito.eq(99L), Mockito.any(Gate.class)))
                    .thenThrow(new RuntimeException("Gate not found"));

            mockMvc.perform(put("/gates/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createTestGate(null, "X", "Y", 10L))))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("PUT /gates/{id} returns 404 on other errors")
        void testUpdateGate_Error() throws Exception {
            Mockito.when(gateService.updateGate(Mockito.eq(3L), Mockito.any(Gate.class)))
                    .thenThrow(new IllegalStateException("oops"));

            mockMvc.perform(put("/gates/3")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createTestGate(null, "X", "Y", 10L))))
                    .andExpect(status().isNotFound());  // now expecting 404
        }
    }

    @Nested
    @DisplayName("DELETE Operations")
    class DeleteOperations {
        @Test
        @DisplayName("DELETE /gates/{id} deletes a gate")
        void testDeleteGate() throws Exception {
            Mockito.doNothing().when(gateService).deleteGate(7L);

            mockMvc.perform(delete("/gates/7"))
                    .andExpect(status().isNoContent());

            Mockito.verify(gateService, Mockito.times(1)).deleteGate(7L);
        }

        @Test
        @DisplayName("DELETE /gates/{id} returns 404 when not found")
        void testDeleteGate_NotFound() throws Exception {
            Mockito.doThrow(new RuntimeException("not found"))
                    .when(gateService).deleteGate(99L);

            mockMvc.perform(delete("/gates/99"))
                    .andExpect(status().isNotFound());
        }
    }
}
