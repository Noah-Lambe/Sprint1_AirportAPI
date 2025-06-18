package com.keyin.airportapi.city;

import com.keyin.airportapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/city")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/city/{id}")
    public City getCityById(@PathVariable long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/city_search")
    public List<City> searchCities(@RequestParam(value = "airport_name", required = false) String airportName,
                                   @RequestParam(value = "passenger_phone", required = false) String passengerPhone) {
        List<City> results = new ArrayList<>();

        if (airportName != null) {
            results = cityService.getCitiesByAirportName(airportName);
        } else if (passengerPhone != null) {
            results = cityService.getCitiesByPassengerPhone(passengerPhone);
        }

        return results;
    }

    @PostMapping("/city")
    public City createCity(@RequestBody City city) {
        return cityService.createCity(city);
    }

    @PutMapping("/city/{id}")
    public ResponseEntity<City> updateCity(@PathVariable long id, @RequestBody City city) {
        City updatedCity = cityService.updateCity(id, city);
        if (updatedCity != null) {
            return ResponseEntity.ok(updatedCity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/city/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable long id) {
        cityService.deleteCityById(id);
        return ResponseEntity.noContent().build();
    }

}
