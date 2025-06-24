package com.keyin.airportapi.airport;

import com.keyin.airportapi.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.keyin.airportapi.city.City;

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
        Optional<Airport> airportOptional = airportService.getAirportById(airportId);
        return airportOptional
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport, @RequestParam Long cityId) {
        Airport createdAirport = airportService.createAirport(airport, cityId);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }


    @PutMapping("/{airportId}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long airportId, @RequestBody Airport airport) {
        Airport updated = airportService.updateAirport(airportId, airport);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{airportId}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long airportId) {
        airportRepository.deleteById(airportId);
        return ResponseEntity.ok().build();
    }
}