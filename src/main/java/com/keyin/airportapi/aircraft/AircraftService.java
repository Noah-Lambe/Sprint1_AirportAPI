package com.keyin.airportapi.aircraft;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    public List<Airport> getAirportsUsedByAircraft(Long aircraftId) throws Exception {
        Aircraft aircraft = aircraftRepository.findByIdWithAirports(aircraftId)
                .orElseThrow(() -> new Exception("Aircraft not found with ID: " + aircraftId));
        return aircraft.getAirports() != null ? aircraft.getAirports() : Collections.emptyList();
    }


    public List<Aircraft> getAllAircraft() {
        return (List<Aircraft>) aircraftRepository.findAll();
    }

    public Optional<Aircraft> getAircraftById(Long id) {
        return aircraftRepository.findById(id);
    }

    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }

    public Aircraft createAircraft(Aircraft newAircraft) {
        return aircraftRepository.save(newAircraft);
    }

    public Aircraft updateAircraft(Long id, Aircraft aircraftData) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        aircraft.setType(aircraftData.getType());
        aircraft.setAirline(aircraftData.getAirline());
        aircraft.setNumberOfPassengers(aircraftData.getNumberOfPassengers());

        if (aircraftData.getAirports() != null) {
            List<Airport> resolvedAirports = aircraftData.getAirports().stream()
                    .map(a -> airportRepository.findById(a.getAirportId())
                            .orElseThrow(() -> new RuntimeException("Airport not found: " + a.getAirportId())))
                    .collect(Collectors.toList());  // mutable list here
            aircraft.setAirports(resolvedAirports);
        }

        return aircraftRepository.save(aircraft);
    }


    public List<Aircraft> getAircraftByPassengerId(Long passengerId) {
        return aircraftRepository.findAircraftByPassengerId(passengerId);
    }

    public List<Aircraft> getAircraftByAirlineName(String airlineName) {
        if (airlineName == null || airlineName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airline name must not be empty");
        }
        return aircraftRepository.findByAirlineName(airlineName);
    }
}