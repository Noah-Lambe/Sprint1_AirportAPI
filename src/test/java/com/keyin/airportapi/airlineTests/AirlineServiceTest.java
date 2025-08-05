package com.keyin.airportapi.airlineTests;

import com.keyin.airportapi.airline.Airline;
import com.keyin.airportapi.airline.AirlineRepository;
import com.keyin.airportapi.airline.AirlineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
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
        @DisplayName("Should return all airlines from repository")
        void testGetAllAirlines() {
            Airline a1 = createTestAirline(1L, "Air Canada");
            Airline a2 = createTestAirline(2L, "WestJet");
            Mockito.when(airlineRepository.findAll())
                    .thenReturn(List.of(a1, a2));

            List<Airline> result = airlineService.getAllAirlines();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Air Canada", result.get(0).getAirlineName());
            assertEquals("WestJet", result.get(1).getAirlineName());
        }

        @Test
        @DisplayName("Should return airline by ID when exists")
        void testGetAirlineById_Found() {
            Airline a = createTestAirline(1L, "Air Canada");
            Mockito.when(airlineRepository.findById(1L))
                    .thenReturn(Optional.of(a));

            Airline result = airlineService.getAirlineById(1L);

            assertNotNull(result);
            assertEquals("Air Canada", result.getAirlineName());
        }

        @Test
        @DisplayName("Should throw RuntimeException when airline ID not found")
        void testGetAirlineById_NotFound() {
            Mockito.when(airlineRepository.findById(99L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> airlineService.getAirlineById(99L));
            assertEquals("Airline not found", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("SEARCH Operations")
    class SearchOperations {
        @Test
        @DisplayName("Should return airlines matching name")
        void testGetAirlinesByName() {
            Airline a = createTestAirline(1L, "Delta");
            Mockito.when(airlineRepository.findByAirlineName("Delta"))
                    .thenReturn(List.of(a));

            List<Airline> result = airlineService.getAirlinesByName("Delta");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Delta", result.get(0).getAirlineName());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when name is empty")
        void testGetAirlinesByName_Empty() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> airlineService.getAirlinesByName("  "));
            assertEquals("Airline name must not be empty", ex.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when name is null")
        void testGetAirlinesByName_Null() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> airlineService.getAirlinesByName(null));
            assertEquals("Airline name must not be empty", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("CREATE Operation")
    class CreateOperation {
        @Test
        @DisplayName("Should save and return new airline")
        void testCreateAirline() {
            Airline newAirline = createTestAirline(null, "KLM");
            Airline savedAirline = createTestAirline(5L, "KLM");
            Mockito.when(airlineRepository.save(Mockito.any(Airline.class)))
                    .thenReturn(savedAirline);

            Airline result = airlineService.createAirline(newAirline);

            assertNotNull(result);
            assertEquals(5L, result.getAirlineId());
            assertEquals("KLM", result.getAirlineName());
        }
    }

    @Nested
    @DisplayName("UPDATE Operation")
    class UpdateOperation {
        @Test
        @DisplayName("Should update existing airline name")
        void testUpdateAirline() {
            Airline existing = createTestAirline(1L, "OldName");
            Airline updates = createTestAirline(null, "NewName");

            Mockito.when(airlineRepository.findById(1L))
                    .thenReturn(Optional.of(existing));
            Mockito.when(airlineRepository.save(Mockito.any(Airline.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Airline result = airlineService.updateAirline(1L, updates);

            assertNotNull(result);
            assertEquals(1L, result.getAirlineId());
            assertEquals("NewName", result.getAirlineName());
        }

        @Test
        @DisplayName("Should throw RuntimeException when updating non-existent airline")
        void testUpdateAirline_NotFound() {
            Airline updates = createTestAirline(null, "NewName");
            Mockito.when(airlineRepository.findById(99L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> airlineService.updateAirline(99L, updates));
            assertEquals("Airline not found", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("DELETE Operation")
    class DeleteOperation {
        @Test
        @DisplayName("Should call deleteById on repository")
        void testDeleteAirline() {
            long id = 1L;
            Mockito.doNothing().when(airlineRepository).deleteById(id);

            airlineService.deleteAirline(id);

            Mockito.verify(airlineRepository, Mockito.times(1)).deleteById(id);
        }
    }
}

