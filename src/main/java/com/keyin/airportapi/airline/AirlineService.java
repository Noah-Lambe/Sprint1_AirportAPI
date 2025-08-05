package com.keyin.airportapi.airline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {
    @Autowired
    private AirlineRepository airlineRepository;

    public List<Airline> getAllAirlines() {
        return (List<Airline>) airlineRepository.findAll();
    }

    public Airline getAirlineById(Long id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));
    }

    public List<Airline> getAirlinesByName(String airlineName) {
        if (airlineName == null || airlineName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airline name must not be empty");
        }
        return airlineRepository.findByAirlineName(airlineName);
    }

    public Airline createAirline(Airline airline) {
        return airlineRepository.save(airline);
    }

    public Airline updateAirline(Long id, Airline updatedAirline) {
        Airline airline = getAirlineById(id);
        airline.setAirlineName(updatedAirline.getAirlineName());
        return airlineRepository.save(airline);
    }

    public void deleteAirline(Long id) {
        airlineRepository.deleteById(id);
    }
}
