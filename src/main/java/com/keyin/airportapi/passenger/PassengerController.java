package com.keyin.airportapi.passenger;

import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByPassenger(@PathVariable Long id) {
        return passengerService.getAirportsUsedByPassenger(id);
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<Aircraft>> getAircraftByPassengerId(@PathVariable long id) {
        Optional<Passenger> passengerOpt = passengerService.getPassengerById(id);

        if (passengerOpt.isPresent()) {
            Passenger passenger = passengerOpt.get();
            return ResponseEntity.ok(List.of(passenger.getAircraftList()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public Passenger addPassenger(@RequestBody Passenger passenger) {
        return passengerService.savePassenger(passenger);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }
}
