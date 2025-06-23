package com.keyin.airportapi.airport;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;

@Service
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private CityRepository cityRepository;

    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        airportRepository.findAll().forEach(airports::add);
        return airports;
    }

    public Optional<Airport> getAirportById(Long id) {
        return airportRepository.findById(id);
    }

    public Airport createAirport(Airport airport, int cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        airport.setCity(city);
        return airportRepository.save(airport);
    }

    public Airport updateAirport(Long id, Airport updatedAirport) {
        Optional<Airport> airportOptional = getAirportById(id);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.setAirportId(updatedAirport.getAirportId());
            airport.setAirportName(updatedAirport.getAirportName());
            airport.setAreaCode(updatedAirport.getAreaCode());
            if (updatedAirport.getCity() != null) {
                airport.setCity(updatedAirport.getCity());
            }
            return airportRepository.save(airport);
        } else {
            updatedAirport.setAirportId(id);
            return airportRepository.save(updatedAirport);
        }
    }

    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }
}
