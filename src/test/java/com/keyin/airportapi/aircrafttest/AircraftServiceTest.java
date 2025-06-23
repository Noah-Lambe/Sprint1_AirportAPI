// AircraftServiceTest.java
package com.keyin.airportapi.aircrafttest;

import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.aircraft.AircraftRepository;
import com.keyin.airportapi.aircraft.AircraftService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftService aircraftService;

    private Aircraft createTestAircraft(Long id, String type, String airline, int passengers) {
        Aircraft aircraft = new Aircraft(type, airline, passengers);
        if (id != null) aircraft.setType(type);
        return aircraft;
    }

    @Test
    @DisplayName("Should return all aircraft from repository")
    public void testGetAllAircraft() {
        Aircraft a1 = createTestAircraft(1L, "Boeing 737", "Air Canada", 150);
        Aircraft a2 = createTestAircraft(2L, "Airbus A320", "WestJet", 180);

        Mockito.when(aircraftRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Aircraft> result = aircraftService.getAllAircraft();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return aircraft by ID if exists")
    public void testGetAircraftById() {
        Aircraft aircraft = createTestAircraft(1L, "Boeing 737", "Air Canada", 150);
        Mockito.when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));

        Optional<Aircraft> result = aircraftService.getAircraftById(1L);

        assertTrue(result.isPresent());
        assertEquals("Air Canada", result.get().getAirlineName());
    }

    @Test
    @DisplayName("Should return empty Optional when aircraft not found")
    public void testGetAircraftById_NotFound() {
        Mockito.when(aircraftRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Aircraft> result = aircraftService.getAircraftById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should create and return a new aircraft")
    public void testCreateAircraft() {
        Aircraft aircraft = createTestAircraft(null, "Boeing 737", "Air Canada", 150);

        Mockito.when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);

        Aircraft result = aircraftService.createAircraft(aircraft);

        assertNotNull(result);
        assertEquals("Air Canada", result.getAirlineName());
    }

    @Test
    @DisplayName("Should delete aircraft by ID")
    public void testDeleteAircraft() {
        long aircraftId = 1L;

        aircraftService.deleteAircraft(aircraftId);

        Mockito.verify(aircraftRepository, Mockito.times(1)).deleteById(aircraftId);
    }

    @Test
    @DisplayName("Should update aircraft when it exists")
    public void testUpdateAircraft() {
        Aircraft existing = createTestAircraft(1L, "Boeing 737", "Air Canada", 150);
        Aircraft updated = createTestAircraft(null, "Airbus A320", "WestJet", 180);

        Mockito.when(aircraftRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(aircraftRepository.save(Mockito.any())).thenReturn(updated);

        Aircraft result = aircraftService.updateAircraft(1L, updated);

        assertNotNull(result);
        assertEquals("WestJet", result.getAirlineName());
    }

    @Test
    @DisplayName("Should return null when updating non-existent aircraft")
    public void testUpdateAircraft_NotFound() {
        Aircraft updated = createTestAircraft(null, "Airbus A320", "WestJet", 180);

        Mockito.when(aircraftRepository.findById(99L)).thenReturn(Optional.empty());

        Aircraft result = aircraftService.updateAircraft(99L, updated);

        assertNull(result);
    }
}