package com.keyin.airportapi.flight;

import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.passenger.PassengerRepository;
import com.keyin.airportapi.passenger.PassengerRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<Flight> getAllFlights() {
        return (List<Flight>) flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    public List<Flight> getFlightsByAirlineId(Long airlineId) {
        return flightRepository.findByAirline_AirlineId(airlineId);
    }

    public List<Flight> getFlightsByOriginAirportId(Long originAirportId) {
        return flightRepository.findByOriginAirport_AirportId(originAirportId);
    }

    public List<Flight> getFlightsByDestinationAirportId(Long destinationAirportId) {
        return flightRepository.findByDestinationAirport_AirportId(destinationAirportId);
    }

    public List<Flight> getFlightsByGateId(Long gateId) {
        return flightRepository.findByGate_GateId(gateId);
    }

    public List<Flight> getFlightsByStatus(String status) {
        return flightRepository.findByStatus(status);
    }

    public List<Flight> getFlightsByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public List<Flight> getFlightsByAircraftId(Long aircraftId) {
        return flightRepository.findByAircraft_AircraftId(aircraftId);
    }

    public List<Flight> getFlightsByDepartureTime(LocalDateTime departureTime) {
        return flightRepository.findByDepartureTime(departureTime);
    }

    public List<Flight> getFlightsByArrivalTime(LocalDateTime arrivalTime) {
        return flightRepository.findByArrivalTime(arrivalTime);
    }

    public List<Flight> getFlightsByDepartureTimeBetween(LocalDateTime start, LocalDateTime end) {
        return flightRepository.findByDepartureTimeBetween(start, end);
    }

    public Flight createFlight(Flight flight) {
        if (flight.getFlightNumber() == null || flight.getDepartureTime() == null || flight.getOriginAirport() == null) {
            throw new IllegalArgumentException("Flight number, departure time, and origin airport are required");
        }
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Long id, Flight flightData) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setFlightNumber(flightData.getFlightNumber());
        flight.setStatus(flightData.getStatus());
        flight.setDepartureTime(flightData.getDepartureTime());
        flight.setArrivalTime(flightData.getArrivalTime());
        flight.setOriginAirport(flightData.getOriginAirport());
        flight.setDestinationAirport(flightData.getDestinationAirport());
        flight.setGate(flightData.getGate());
        flight.setAircraft(flightData.getAircraft());
        flight.setAirline(flightData.getAirline());

        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public Flight addPassengerToFlight(Long flightId, PassengerRequest request) {
        Flight flight = getFlightById(flightId);

        Passenger pax;
        if (request.getPassengerId() != null) {
            // fetch by ID
            pax = passengerRepository.findById(request.getPassengerId())
                    .orElseThrow(() -> new NoSuchElementException("Passenger not found"));
        } else {
            pax = new Passenger();
            pax.setFirstName(request.getFirstName());
            pax.setLastName(request.getLastName());
            pax.setPhoneNumber(request.getPhoneNumber());

            City city = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new NoSuchElementException("City not found"));
            pax.setCity(city);

            // Save the new passenger
            pax = passengerRepository.save(pax);
        }

        if (flight.getPassengers() == null) {
            flight.setPassengers(new ArrayList<>());
        }
        flight.getPassengers().add(pax);

        if (pax.getFlights() == null) {
            pax.setFlights(new ArrayList<>());
        }
        pax.getFlights().add(flight);

        return flightRepository.save(flight);
    }

    @Transactional
    public void removePassengerFromFlight(Long flightId, Long passengerId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        // remove association on both sides
        if (flight.getPassengers() != null) {
            flight.getPassengers().remove(passenger);
        }
        if (passenger.getFlights() != null) {
            passenger.getFlights().remove(flight);
        }

        flightRepository.save(flight);
    }


}
