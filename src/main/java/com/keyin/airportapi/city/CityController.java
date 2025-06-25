package com.keyin.airportapi.city;

import com.keyin.airportapi.airport.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        try {
            return ResponseEntity.ok(cityService.getAllCities());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByCityId(@PathVariable Long id) {
        try {
            return cityService.getAirportsByCityId(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving airports for city with ID: " + id, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id) {
        try {
            City city = cityService.getCityById(id);
            if (city != null) {
                return ResponseEntity.ok(city);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/city_search")
    public ResponseEntity<List<City>> searchCities(@RequestParam(value = "airport_name", required = false) String airportName,
                                                   @RequestParam(value = "passenger_phone", required = false) String passengerPhone) {
        try {
            List<City> results = new ArrayList<>();

            if (airportName != null) {
                results = cityService.getCitiesByAirportName(airportName);
            } else if (passengerPhone != null) {
                results = cityService.getCitiesByPassengerPhone(passengerPhone);
            }

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        try {
            City createdCity = cityService.createCity(city);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Location", "/city/" + createdCity.getId())
                    .body(createdCity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable long id, @RequestBody City city) {
        try {
            City updatedCity = cityService.updateCity(id, city);
            if (updatedCity != null) {
                return ResponseEntity.ok(updatedCity);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable long id) {
        try {
            cityService.deleteCityById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
