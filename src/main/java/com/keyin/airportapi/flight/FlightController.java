package com.keyin.airportapi.flight;


import com.keyin.airportapi.passenger.PassengerRepository;
import com.keyin.airportapi.passenger.PassengerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

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

    @GetMapping("/byPassenger/{passengerId}")
    public List<Flight> getFlightsByPassenger(@PathVariable Long passengerId) {
        return flightRepository.findByPassengers_Id(passengerId);
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
    public ResponseEntity<Flight> addPassengerToFlight(
            @PathVariable Long flightId,
            @RequestBody PassengerRequest request
    ) {
        // Delegate to the service so your MockMvc stub is used
        Flight updated = flightService.addPassengerToFlight(flightId, request);
        return ResponseEntity.ok(updated);
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

    @GetMapping("/search")
    public ResponseEntity<Page<Flight>> searchFlights(
            @RequestParam(required = false) Long originAirportId,
            @RequestParam(required = false) Long destinationAirportId,
            @RequestParam(required = false) Long airlineId,
            @RequestParam(required = false) Long gateId,
            @RequestParam(required = false) Long aircraftId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) LocalDateTime departStart,
            @RequestParam(required = false) LocalDateTime departEnd,
            @RequestParam(required = false) LocalDateTime arriveStart,
            @RequestParam(required = false) LocalDateTime arriveEnd,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "departureTime,asc") String sort
    ) {
        try {
            String[] parts = sort.split(",");
            String sortField = parts[0];
            Sort.Direction dir = (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;

            Specification<Flight> spec = Specification
                    .where(FlightSpecifications.hasOriginAirportId(originAirportId))
                    .and(FlightSpecifications.hasDestinationAirportId(destinationAirportId))
                    .and(FlightSpecifications.hasAirlineId(airlineId))
                    .and(FlightSpecifications.hasGateId(gateId))
                    .and(FlightSpecifications.hasAircraftId(aircraftId))
                    .and(FlightSpecifications.hasStatus(status))
                    .and(FlightSpecifications.flightNumberLike(flightNumber))
                    .and(FlightSpecifications.departsOnOrAfter(departStart))
                    .and(FlightSpecifications.departsOnOrBefore(departEnd))
                    .and(FlightSpecifications.arrivesOnOrAfter(arriveStart))
                    .and(FlightSpecifications.arrivesOnOrBefore(arriveEnd));

            Page<Flight> pageResult = flightRepository.findAll(
                    spec, PageRequest.of(page, size, Sort.by(dir, sortField))
            );
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{flightId}/passengers/{passengerId}")
    public ResponseEntity<Void> removePassengerFromFlight(
            @PathVariable Long flightId,
            @PathVariable Long passengerId
    ) {
        flightService.removePassengerFromFlight(flightId, passengerId);
        return ResponseEntity.noContent().build();
    }

}