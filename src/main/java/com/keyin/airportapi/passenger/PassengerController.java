package com.keyin.airportapi.passenger;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @Autowired
    PassengerRepository passengerRepository;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String,Object>> getOrCreateByUserId(@PathVariable Long userId) {
        Passenger p = passengerRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Passenger np = new Passenger();
                    np.setUserId(userId);
                    return passengerRepository.save(np);
                });

        // Use a HashMap so we can include nulls
        Map<String,Object> dto = new HashMap<>();
        dto.put("passengerId",  p.getId());
        dto.put("firstName",    p.getFirstName());
        dto.put("lastName",     p.getLastName());
        dto.put("phoneNumber",  p.getPhoneNumber());
        dto.put("cityId",       p.getCity() != null ? p.getCity().getId() : null);

        return ResponseEntity.ok(dto);
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
