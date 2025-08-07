package com.keyin.airportapi.flightTests;

import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.flight.FlightService;
import com.keyin.airportapi.flight.FlightRepository;
import com.keyin.airportapi.flight.Flight;
import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.passenger.PassengerRepository;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airline.Airline;
import com.keyin.airportapi.gate.Gate;
import com.keyin.airportapi.aircraft.Aircraft;

import com.keyin.airportapi.passenger.PassengerRequest;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.NoSuchElementException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    private FlightService flightService;

    // Helper to create minimal Flight
    private Flight createTestFlight(Long id, String flightNumber) {
        Flight f = new Flight();
        if (id != null) {
            f.setFlightId(id);
        }
        f.setFlightNumber(flightNumber);
        f.setStatus("ON_TIME");
        LocalDateTime now = LocalDateTime.now();
        f.setDepartureTime(now);
        f.setArrivalTime(now.plusHours(2));
        f.setOriginAirport(new Airport());
        f.setDestinationAirport(new Airport());
        f.setGate(new Gate());
        f.setAircraft(new Aircraft());
        f.setAirline(new Airline());
        // Use a mutable list for passengers
        f.setPassengers(new ArrayList<>());
        return f;
    }

    @Nested
    @DisplayName("GET Operations")
    class GetOperations {
        @Test
        @DisplayName("Should return all flights")
        void testGetAllFlights() {
            Flight f1 = createTestFlight(1L, "FL100");
            Flight f2 = createTestFlight(2L, "FL200");
            Mockito.when(flightRepository.findAll())
                    .thenReturn(List.of(f1, f2));

            List<Flight> result = flightService.getAllFlights();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("FL100", result.get(0).getFlightNumber());
            assertEquals("FL200", result.get(1).getFlightNumber());
        }

        @Test
        @DisplayName("Should return flight by ID when exists")
        void testGetFlightById_Found() {
            Flight f = createTestFlight(1L, "FL100");
            Mockito.when(flightRepository.findById(1L))
                    .thenReturn(Optional.of(f));

            Flight result = flightService.getFlightById(1L);

            assertNotNull(result);
            assertEquals("FL100", result.getFlightNumber());
        }

        @Test
        @DisplayName("Should throw when flight ID not found")
        void testGetFlightById_NotFound() {
            Mockito.when(flightRepository.findById(99L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> flightService.getFlightById(99L));
            assertEquals("Flight not found", ex.getMessage());
        }

        @Test
        @DisplayName("Should return flights by airline ID")
        void testGetFlightsByAirlineId() {
            Flight f = createTestFlight(1L, "FL100");
            Mockito.when(flightRepository.findByAirline_AirlineId(5L))
                    .thenReturn(List.of(f));

            List<Flight> result = flightService.getFlightsByAirlineId(5L);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("FL100", result.get(0).getFlightNumber());
        }
    }

    @Nested
    @DisplayName("CREATE Operation")
    class CreateOperation {
        @Test
        @DisplayName("Should save and return new flight when all required fields present")
        void testCreateFlight_Success() {
            Flight newFlight = createTestFlight(null, "FL300");
            Mockito.when(flightRepository.save(Mockito.any(Flight.class)))
                    .thenAnswer(inv -> {
                        Flight arg = inv.getArgument(0);
                        arg.setFlightId(3L);
                        return arg;
                    });

            Flight result = flightService.createFlight(newFlight);

            assertNotNull(result);
            assertEquals(3L, result.getFlightId());
            assertEquals("FL300", result.getFlightNumber());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when missing required fields")
        void testCreateFlight_Invalid() {
            Flight bad = new Flight();
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> flightService.createFlight(bad));
            assertEquals(
                    "Flight number, departure time, and origin airport are required",
                    ex.getMessage()
            );
        }
    }

    @Nested
    @DisplayName("UPDATE Operation")
    class UpdateOperation {
        @Test
        @DisplayName("Should update existing flight")
        void testUpdateFlight() {
            Flight existing = createTestFlight(1L, "OLD100");
            Flight updates = createTestFlight(null, "NEW100");

            Mockito.when(flightRepository.findById(1L))
                    .thenReturn(Optional.of(existing));
            Mockito.when(flightRepository.save(Mockito.any(Flight.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            Flight result = flightService.updateFlight(1L, updates);

            assertNotNull(result);
            assertEquals(1L, result.getFlightId());
            assertEquals("NEW100", result.getFlightNumber());
            assertEquals("ON_TIME", result.getStatus());
        }

        @Test
        @DisplayName("Should throw when updating non-existent flight")
        void testUpdateFlight_NotFound() {
            Flight updates = createTestFlight(null, "NEW100");
            Mockito.when(flightRepository.findById(99L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> flightService.updateFlight(99L, updates));
            assertEquals("Flight not found", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("DELETE Operation")
    class DeleteOperation {
        @Test
        @DisplayName("Should call deleteById on repository")
        void testDeleteFlight() {
            Mockito.doNothing().when(flightRepository).deleteById(1L);

            flightService.deleteFlight(1L);

            Mockito.verify(flightRepository, Mockito.times(1))
                    .deleteById(1L);
        }
    }

    @Nested @DisplayName("ADD PASSENGER Operation")
    class AddPassengerOperation {
        @Test
        @DisplayName("Should add new passenger to flight when passenger has no ID")
        void testAddPassenger_NewPassenger() {
            // 1) set up a Flight
            Flight f = createTestFlight(1L, "FL400");
            when(flightRepository.findById(1L))
                    .thenReturn(Optional.of(f));

            // 2) stub the city lookup
            City springfield = new City(1L, "Springfield", "IL", 100_000);
            when(cityRepository.findById(1L))
                    .thenReturn(Optional.of(springfield));

            // 3) stub saving of the passenger (assign an ID)
            when(passengerRepository.save(any(Passenger.class)))
                    .thenAnswer(inv -> {
                        Passenger p = inv.getArgument(0);
                        p.setId(10L);
                        return p;
                    });

            // 4) stub saving of the flight
            when(flightRepository.save(any(Flight.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            // exercise
            PassengerRequest req = new PassengerRequest("John", "Doe", "555-1234", 1L);
            Flight result = flightService.addPassengerToFlight(1L, req);

            assertThat(result.getPassengers())
                    .extracting(Passenger::getId)
                    .containsExactly(10L);
        }

        @Test
        @DisplayName("Should add existing passenger to flight when passenger has ID")
        void testAddPassenger_ExistingPassenger() {
            Flight f = createTestFlight(1L, "FL500");
            when(flightRepository.findById(1L))
                    .thenReturn(Optional.of(f));

            // stub lookup of existing passenger — no city lookup needed here
            Passenger existing = new Passenger();
            existing.setId(20L);
            when(passengerRepository.findById(20L))
                    .thenReturn(Optional.of(existing));

            when(flightRepository.save(any(Flight.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            PassengerRequest req = new PassengerRequest();
            req.setPassengerId(20L);
            Flight result = flightService.addPassengerToFlight(1L, req);

            assertThat(result.getPassengers())
                    .extracting(Passenger::getId)
                    .containsExactly(20L);
        }

        @Test
        @DisplayName("Should throw when adding non-existent passenger by ID")
        void testAddPassenger_PassengerNotFound() {
            when(flightRepository.findById(1L))
                    .thenReturn(Optional.of(createTestFlight(1L, "FL600")));

            PassengerRequest req = new PassengerRequest();
            req.setPassengerId(99L);

            when(passengerRepository.findById(99L))
                    .thenReturn(Optional.empty());

                    assertThrows(NoSuchElementException.class,
                            () -> flightService.addPassengerToFlight(1L, req),
                            "Passenger not found");
        }
    }
}
