package com.keyin.airportapi.passenger;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}/flights")
    public List<Flight> getFlightsByPassenger(@PathVariable Long id) {
        return passengerService.getFlightsByPassengerId(id);
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
