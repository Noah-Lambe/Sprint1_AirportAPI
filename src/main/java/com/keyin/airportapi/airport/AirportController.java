package com.keyin.airportapi.airport;

import com.keyin.airportapi.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.keyin.airportapi.airport.city.*;

@RestController
@CrossOrigin
public class AirportController {
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private AirportService airportService;
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @GetMapping("/{airportId}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long airportId) {
        Optional<Airport> airportOptional = airportRepository.findById(airportId);
        return airportOptional
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport, @RequestParam int cityId) {
        City city = cityRepository.findByAId(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        airport.setCity(city);
        return new ResponseEntity<>(airportRepository.save(airport), HttpStatus.CREATED);
    }

    @PutMapping("/{airportId}")
    public ResponseEntity<Airport> updateAirport(@PathVariable int airportId, @RequestBody Airport airport) {
        Airport updated = airportService.updateAirport(airportId, airport);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Airport> deleteAirport(@PathVariable Airport airport) {
        airportRepository.delete(airport);
        return new ResponseEntity<>(HttpStatus.OK);
    } // May need to change to
}