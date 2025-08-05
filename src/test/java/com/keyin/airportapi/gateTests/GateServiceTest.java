package com.keyin.airportapi.gateTests;

import com.keyin.airportapi.gate.GateService;
import com.keyin.airportapi.gate.GateRepository;
import com.keyin.airportapi.gate.Gate;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.airport.Airport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GateServiceTest {

    @Mock
    private GateRepository gateRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private GateService gateService;

    // Helper to build a Gate with optional airport
    private Gate createTestGate(Long id, String terminal, String gateName, Long airportId) {
        Gate gate = new Gate();
        if (id != null) gate.setGateId(id);
        gate.setTerminal(terminal);
        gate.setGateName(gateName);
        if (airportId != null) {
            Airport a = new Airport();
            a.setAirportId(airportId);
            gate.setAirport(a);
        } else {
            gate.setAirport(null);
        }
        return gate;
    }

    @Nested
    @DisplayName("GET Operations")
    class GetOperations {
        @Test
        @DisplayName("Should return all gates")
        void testGetAllGates() {
            Gate g1 = createTestGate(1L, "T1", "G1", 10L);
            Gate g2 = createTestGate(2L, "T2", "G2", 20L);
            Mockito.when(gateRepository.findAll())
                    .thenReturn(List.of(g1, g2));

            List<Gate> result = gateService.getAllGates();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("T1", result.get(0).getTerminal());
            assertEquals("G2", result.get(1).getGateName());
        }

        @Test
        @DisplayName("Should return gate by ID when exists")
        void testGetGateById_Found() {
            Gate g = createTestGate(1L, "T1", "G1", 10L);
            Mockito.when(gateRepository.findById(1L))
                    .thenReturn(Optional.of(g));

            Gate result = gateService.getGateById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getGateId());
            assertEquals("G1", result.getGateName());
        }

        @Test
        @DisplayName("Should throw when gate ID not found")
        void testGetGateById_NotFound() {
            Mockito.when(gateRepository.findById(99L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gateService.getGateById(99L));
            assertEquals("Gate not found", ex.getMessage());
        }

        @Test
        @DisplayName("Should return gates by airport ID")
        void testGetGatesByAirportId() {
            Gate g = createTestGate(1L, "T1", "G1", 5L);
            Mockito.when(gateRepository.findByAirport_AirportId(5L))
                    .thenReturn(List.of(g));

            List<Gate> result = gateService.getGatesByAirportId(5L);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(5L, result.get(0).getAirport().getAirportId());
        }

        @Test
        @DisplayName("Should return gates by terminal")
        void testGetGatesByTerminal() {
            Gate g = createTestGate(1L, "A", "G1", 10L);
            Mockito.when(gateRepository.findByTerminal("A"))
                    .thenReturn(List.of(g));

            List<Gate> result = gateService.getGatesByTerminal("A");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("A", result.get(0).getTerminal());
        }

        @Test
        @DisplayName("Should return gates by gate name")
        void testGetGatesByGateName() {
            Gate g = createTestGate(1L, "T1", "X5", 10L);
            Mockito.when(gateRepository.findByGateName("X5"))
                    .thenReturn(List.of(g));

            List<Gate> result = gateService.getGatesByGateName("X5");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("X5", result.get(0).getGateName());
        }
    }

    @Nested
    @DisplayName("CREATE Operation")
    class CreateOperation {
        @Test
        @DisplayName("Should create gate when airport exists")
        void testCreateGate_Success() {
            Gate toCreate = createTestGate(null, "T1", "G1", 100L);
            Airport a = new Airport();
            a.setAirportId(100L);
            Mockito.when(airportRepository.findById(100L))
                    .thenReturn(Optional.of(a));
            Mockito.when(gateRepository.save(Mockito.any(Gate.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            Gate result = gateService.createGate(toCreate);

            assertNotNull(result);
            assertEquals("G1", result.getGateName());
            assertEquals(100L, result.getAirport().getAirportId());
        }

        @Test
        @DisplayName("Should throw when airport ID missing")
        void testCreateGate_NoAirport() {
            Gate bad = createTestGate(null, "T1", "G1", null);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gateService.createGate(bad));
            assertEquals("Airport ID is required for Gate creation", ex.getMessage());
        }

        @Test
        @DisplayName("Should throw when airport not found")
        void testCreateGate_AirportNotFound() {
            Gate toCreate = createTestGate(null, "T1", "G1", 200L);
            Mockito.when(airportRepository.findById(200L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gateService.createGate(toCreate));
            assertEquals("Airport not found", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("UPDATE Operation")
    class UpdateOperation {
        @Test
        @DisplayName("Should update existing gate")
        void testUpdateGate() {
            Gate existing = createTestGate(1L, "T1", "G1", 10L);
            Gate updates  = createTestGate(null, "T2", "G2", 10L);
            Mockito.when(gateRepository.findById(1L))
                    .thenReturn(Optional.of(existing));
            Mockito.when(gateRepository.save(Mockito.any(Gate.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            Gate result = gateService.updateGate(1L, updates);

            assertNotNull(result);
            assertEquals(1L, result.getGateId());
            assertEquals("T2", result.getTerminal());
            assertEquals("G2", result.getGateName());
        }

        @Test
        @DisplayName("Should throw when updating non-existent gate")
        void testUpdateGate_NotFound() {
            Gate updates = createTestGate(null, "T2", "G2", 10L);
            Mockito.when(gateRepository.findById(99L))
                    .thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> gateService.updateGate(99L, updates));
            assertEquals("Gate not found", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("DELETE Operation")
    class DeleteOperation {
        @Test
        @DisplayName("Should call deleteById on repository")
        void testDeleteGate() {
            Mockito.doNothing().when(gateRepository).deleteById(5L);

            gateService.deleteGate(5L);

            Mockito.verify(gateRepository, Mockito.times(1)).deleteById(5L);
        }
    }
}
