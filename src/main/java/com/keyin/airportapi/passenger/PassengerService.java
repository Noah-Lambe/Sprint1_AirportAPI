package com.keyin.airportapi.passenger;

import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepo;

    @Autowired
    private PassengerRepository passengerRepository;

    public List<Passenger> getAllPassengers() {
        return passengerRepo.findAll();
    }

    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepo.findById(id);
    }

    public Passenger savePassenger(Passenger passenger) {
        return passengerRepo.save(passenger);
    }

    public void deletePassenger(Long id) {
        passengerRepo.deleteById(id);
    }

    public List<Airport> getAirportsUsedByPassenger(Long passengerId) {
        Passenger passenger = passengerRepo.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        Set<Airport> airports = new HashSet<>();
        for (Aircraft aircraft : passenger.getAircraftList()) {
            airports.addAll(aircraft.getAirports());
        }

        return new ArrayList<>(airports);
    }

    public List<Flight> getFlightsByPassengerId(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        return passenger.getFlights();
    }
}
