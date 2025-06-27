package com.keyin.airportapi.aircraft;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private AircraftRepository aircraftRepository;


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

    @GetMapping("/{aircraftId}/airports")
    public ResponseEntity<List<Airport>> getAirportsUsedByAircraft(@PathVariable("aircraftId") Long aircraftId) {
        try {
            List<Airport> airports = aircraftService.getAirportsUsedByAircraft(aircraftId);
            return ResponseEntity.ok(airports);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @PostMapping("/passenger/{id}/aircraft")
    public ResponseEntity<?> addAircraftToPassenger(
            @PathVariable Long id,
            @RequestBody Map<String, List<Long>> request) {

        List<Long> aircraftIds = request.get("aircraftIds");

        Passenger passenger = passengerRepository.findById(id).orElseThrow();
        List<Aircraft> aircraftList = (List<Aircraft>) aircraftRepository.findAllById(aircraftIds);

        passenger.getAircraft().addAll(aircraftList);
        passengerRepository.save(passenger);

        return ResponseEntity.ok(passenger);
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
