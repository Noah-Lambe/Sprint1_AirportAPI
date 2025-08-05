package com.keyin.airportapi.flight;


import com.keyin.airportapi.passenger.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        try {
            List<Flight> flights = flightService.getAllFlights();
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        try {
            Flight flight = flightService.getFlightById(id);
            return ResponseEntity.ok(flight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<Flight>> getFlightsByAirlineId(@PathVariable Long airlineId) {
        try {
            List<Flight> flights = flightService.getFlightsByAirlineId(airlineId);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/origin/{originAirportId}")
    public ResponseEntity<List<Flight>> getFlightsByOriginAirportId(@PathVariable Long originAirportId) {
        try {
            List<Flight> flights = flightService.getFlightsByOriginAirportId(originAirportId);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/destination/{destinationAirportId}")
    public ResponseEntity<List<Flight>> getFlightsByDestinationAirportId(@PathVariable Long destinationAirportId) {
        try {
            List<Flight> flights = flightService.getFlightsByDestinationAirportId(destinationAirportId);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/gate/{gateId}")
    public ResponseEntity<List<Flight>> getFlightsByGateId(@PathVariable Long gateId) {
        try {
            List<Flight> flights = flightService.getFlightsByGateId(gateId);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Flight>> getFlightsByStatus(@PathVariable String status) {
        try {
            List<Flight> flights = flightService.getFlightsByStatus(status);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/flightNumber/{flightNumber}")
    public ResponseEntity<List<Flight>> getFlightsByFlightNumber(@PathVariable String flightNumber) {
        try {
            List<Flight> flights = flightService.getFlightsByFlightNumber(flightNumber);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<Flight>> getFlightsByAircraftId(@PathVariable Long aircraftId) {
        try {
            List<Flight> flights = flightService.getFlightsByAircraftId(aircraftId);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/departureTime")
    public ResponseEntity<List<Flight>> getFlightsByDepartureTime(@RequestParam("time") LocalDateTime departureTime) {
        try {
            List<Flight> flights = flightService.getFlightsByDepartureTime(departureTime);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/arrivalTime")
    public ResponseEntity<List<Flight>> getFlightsByArrivalTime(@RequestParam("time") LocalDateTime arrivalTime) {
        try {
            List<Flight> flights = flightService.getFlightsByArrivalTime(arrivalTime);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/departureTimeBetween")
    public ResponseEntity<List<Flight>> getFlightsByDepartureTimeBetween(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        try {
            List<Flight> flights = flightService.getFlightsByDepartureTimeBetween(start, end);
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{flightId}/addPassenger")
    public ResponseEntity<Flight> addPassengerToFlight(@PathVariable Long flightId, @RequestBody Passenger passenger) {
        try {
            Flight updatedFlight = flightService.addPassengerToFlight(flightId, passenger);
            return ResponseEntity.ok(updatedFlight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        try {
            Flight createdFlight = flightService.createFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flightData) {
        try {
            Flight updatedFlight = flightService.updateFlight(id, flightData);
            return ResponseEntity.ok(updatedFlight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        try {
            flightService.deleteFlight(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}