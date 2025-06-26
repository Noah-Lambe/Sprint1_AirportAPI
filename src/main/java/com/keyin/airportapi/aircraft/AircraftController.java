package com.keyin.airportapi.aircraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftService.getAllAircraft();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftService.getAircraftById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/passenger/{passengerId}")
    public List<Aircraft> getAircraftByPassengerId(@PathVariable Long passengerId) {
        return aircraftService.getAircraftByPassengerId(passengerId);
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @PutMapping("/{id}")
    public Aircraft updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        return aircraftService.updateAircraft(id, aircraft);
    }

    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
    }
}
