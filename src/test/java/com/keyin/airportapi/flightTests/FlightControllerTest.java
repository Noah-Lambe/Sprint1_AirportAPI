package com.keyin.airportapi.flightTests;

import com.keyin.airportapi.flight.FlightController;
import com.keyin.airportapi.flight.FlightService;
import com.keyin.airportapi.flight.Flight;
import com.keyin.airportapi.passenger.Passenger;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightService flightService;

    // Helper to create minimal Flight
    private Flight createTestFlight(Long id, String flightNumber) {
        Flight f = new Flight();
        if (id != null) {
            f.setFlightId(id);
        }
        f.setFlightNumber(flightNumber);
        f.setStatus("ON_TIME");
        LocalDateTime now = LocalDateTime.of(2025, 8, 5, 12, 0);
        f.setDepartureTime(now);
        f.setArrivalTime(now.plusHours(2));
        f.setOriginAirport(null);
        f.setDestinationAirport(null);
        f.setGate(null);
        f.setAircraft(null);
        f.setAirline(null);
        f.setPassengers(new ArrayList<>());
        return f;
    }

    // Helper to create a Passenger
    private Passenger createTestPassenger(Long id) {
        Passenger p = new Passenger();
        if (id != null) {
            p.setId(id);
        }
        p.setFirstName("Test");
        p.setLastName("User");
        p.setFlights(new ArrayList<>());
        return p;
    }

    @Nested
    @DisplayName("GET Operations")
    class GetOperations {

        @Test
        @DisplayName("GET /flights returns list of flights")
        void testGetAllFlights() throws Exception {
            Flight f1 = createTestFlight(1L, "FL100");
            Flight f2 = createTestFlight(2L, "FL200");
            Mockito.when(flightService.getAllFlights())
                    .thenReturn(List.of(f1, f2));

            mockMvc.perform(get("/flights"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].flightNumber").value("FL100"))
                    .andExpect(jsonPath("$[1].flightNumber").value("FL200"));
        }

        @Test
        @DisplayName("GET /flights/{id} returns flight by ID")
        void testGetFlightById_Found() throws Exception {
            Flight f = createTestFlight(1L, "FL100");
            Mockito.when(flightService.getFlightById(1L)).thenReturn(f);

            mockMvc.perform(get("/flights/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.flightId").value(1))
                    .andExpect(jsonPath("$.flightNumber").value("FL100"));
        }

        @Test
        @DisplayName("GET /flights/{id} returns 404 when not found")
        void testGetFlightById_NotFound() throws Exception {
            Mockito.when(flightService.getFlightById(99L))
                    .thenThrow(new RuntimeException("Flight not found"));

            mockMvc.perform(get("/flights/99"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("GET /flights/airline/{airlineId} filters by airline")
        void testGetFlightsByAirlineId() throws Exception {
            Flight f = createTestFlight(1L, "F100");
            Mockito.when(flightService.getFlightsByAirlineId(5L))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/airline/5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].flightNumber").value("F100"));
        }

        @Test
        @DisplayName("GET /flights/origin/{originAirportId} filters by origin")
        void testGetFlightsByOrigin() throws Exception {
            Flight f = createTestFlight(1L, "F200");
            Mockito.when(flightService.getFlightsByOriginAirportId(3L))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/origin/3"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].flightNumber").value("F200"));
        }

        @Test
        @DisplayName("GET /flights/destination/{destinationAirportId} filters by destination")
        void testGetFlightsByDestination() throws Exception {
            Flight f = createTestFlight(1L, "F300");
            Mockito.when(flightService.getFlightsByDestinationAirportId(4L))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/destination/4"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].flightNumber").value("F300"));
        }

        @Test
        @DisplayName("GET /flights/status/{status} filters by status")
        void testGetFlightsByStatus() throws Exception {
            Flight f = createTestFlight(1L, "F400");
            Mockito.when(flightService.getFlightsByStatus("ON_TIME"))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/status/ON_TIME"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].status").value("ON_TIME"));
        }

        @Test
        @DisplayName("GET /flights/flightNumber/{flightNumber} filters by flight number")
        void testGetFlightsByFlightNumber() throws Exception {
            Flight f = createTestFlight(1L, "X123");
            Mockito.when(flightService.getFlightsByFlightNumber("X123"))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/flightNumber/X123"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].flightNumber").value("X123"));
        }

        @Test
        @DisplayName("GET /flights/aircraft/{aircraftId} filters by aircraft")
        void testGetFlightsByAircraftId() throws Exception {
            Flight f = createTestFlight(1L, "A500");
            Mockito.when(flightService.getFlightsByAircraftId(7L))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/aircraft/7"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].flightNumber").value("A500"));
        }

        @Test
        @DisplayName("GET /flights/departureTimeBetween filters by departure window")
        void testGetFlightsByDepartureTimeBetween() throws Exception {
            LocalDateTime start = LocalDateTime.of(2025,8,5,8,0);
            LocalDateTime end   = LocalDateTime.of(2025,8,5,10,0);
            Flight f = createTestFlight(1L, "W100");
            Mockito.when(flightService.getFlightsByDepartureTimeBetween(start, end))
                    .thenReturn(List.of(f));

            mockMvc.perform(get("/flights/departureTimeBetween")
                            .param("start", start.toString())
                            .param("end",   end.toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].flightNumber").value("W100"));
        }
    }

    @Nested
    @DisplayName("POST Operations")
    class PostOperations {
        @Test
        @DisplayName("POST /flights creates a new flight")
        void testCreateFlight() throws Exception {
            Flight in = createTestFlight(null, "NEW1");
            Flight out = createTestFlight(9L, "NEW1");
            Mockito.when(flightService.createFlight(Mockito.any(Flight.class)))
                    .thenReturn(out);

            mockMvc.perform(post("/flights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(in)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.flightId").value(9))
                    .andExpect(jsonPath("$.flightNumber").value("NEW1"));
        }

        @Test
        @DisplayName("POST /flights/{flightId}/addPassenger adds passenger")
        void testAddPassengerToFlight() throws Exception {
            Passenger p = createTestPassenger(null);
            Flight out = createTestFlight(1L, "FLX");
            out.getPassengers().add(p);
            Mockito.when(flightService.addPassengerToFlight(Mockito.eq(1L), Mockito.any(Passenger.class)))
                    .thenReturn(out);

            mockMvc.perform(post("/flights/1/addPassenger")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(p)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.passengers.length()").value(1));
        }
    }

    @Nested
    @DisplayName("PUT Operations")
    class PutOperations {
        @Test
        @DisplayName("PUT /flights/{id} updates an existing flight")
        void testUpdateFlight() throws Exception {
            Flight in = createTestFlight(null, "UPD1");
            Flight out = createTestFlight(2L, "UPD1");
            Mockito.when(flightService.updateFlight(Mockito.eq(2L), Mockito.any(Flight.class)))
                    .thenReturn(out);

            mockMvc.perform(put("/flights/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(in)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.flightId").value(2))
                    .andExpect(jsonPath("$.flightNumber").value("UPD1"));
        }

        @Test
        @DisplayName("PUT /flights/{id} returns 404 for non-existent flight")
        void testUpdateFlight_NotFound() throws Exception {
            Mockito.when(flightService.updateFlight(Mockito.eq(99L), Mockito.any(Flight.class)))
                    .thenThrow(new RuntimeException("Flight not found"));

            mockMvc.perform(put("/flights/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createTestFlight(null,"X"))))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE Operations")
    class DeleteOperations {
        @Test
        @DisplayName("DELETE /flights/{id} deletes a flight")
        void testDeleteFlight() throws Exception {
            Mockito.doNothing().when(flightService).deleteFlight(3L);

            mockMvc.perform(delete("/flights/3"))
                    .andExpect(status().isOk());

            Mockito.verify(flightService, Mockito.times(1)).deleteFlight(3L);
        }

        @Test
        @DisplayName("DELETE /flights/{id} returns 404 for non-existent flight")
        void testDeleteFlight_NotFound() throws Exception {
            Mockito.doThrow(new RuntimeException("Flight not found"))
                    .when(flightService).deleteFlight(99L);

            mockMvc.perform(delete("/flights/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {
        @Test
        @DisplayName("GET /flights returns 500 on service error")
        void testGetAllFlights_InternalError() throws Exception {
            Mockito.when(flightService.getAllFlights()).thenThrow(new RuntimeException("DB down"));

            mockMvc.perform(get("/flights"))
                    .andExpect(status().isInternalServerError());
        }
    }
}
