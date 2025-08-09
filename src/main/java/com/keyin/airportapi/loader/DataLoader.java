package com.keyin.airportapi.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.airportapi.aircraft.*;
import com.keyin.airportapi.airline.*;
import com.keyin.airportapi.airport.*;
import com.keyin.airportapi.city.*;
import com.keyin.airportapi.flight.*;
import com.keyin.airportapi.gate.*;
import com.keyin.airportapi.passenger.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final FlightRepository flightRepository;
    private final GateRepository gateRepository;
    private final PassengerRepository passengerRepository;

    public DataLoader(AircraftRepository aircraftRepository,
                      AirlineRepository airlineRepository,
                      AirportRepository airportRepository,
                      CityRepository cityRepository,
                      FlightRepository flightRepository,
                      GateRepository gateRepository,
                      PassengerRepository passengerRepository) {
        this.aircraftRepository = aircraftRepository;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
        this.flightRepository = flightRepository;
        this.gateRepository = gateRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        if (aircraftRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/aircraft.json");
            if (stream != null) {
                List<Aircraft> aircraft = Arrays.asList(mapper.readValue(stream, Aircraft[].class));
                aircraftRepository.saveAll(aircraft);
            }
        }

        if (airlineRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/airline.json");
            if (stream != null) {
                List<Airline> airlines = Arrays.asList(mapper.readValue(stream, Airline[].class));
                airlineRepository.saveAll(airlines);
            }
        }

        if (airportRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/airport.json");
            if (stream != null) {
                List<Airport> airports = Arrays.asList(mapper.readValue(stream, Airport[].class));
                airportRepository.saveAll(airports);
            }
        }

        if (cityRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/city.json");
            if (stream != null) {
                List<City> cities = Arrays.asList(mapper.readValue(stream, City[].class));
                cityRepository.saveAll(cities);
            }
        }

        if (flightRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/flight.json");
            if (stream != null) {
                List<Flight> flights = Arrays.asList(mapper.readValue(stream, Flight[].class));
                flightRepository.saveAll(flights);
            }
        }

        if (gateRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/gate.json");
            if (stream != null) {
                List<Gate> gates = Arrays.asList(mapper.readValue(stream, Gate[].class));
                gateRepository.saveAll(gates);
            }
        }

        if (passengerRepository.count() == 0) {
            InputStream stream = getClass().getResourceAsStream("/passenger.json");
            if (stream != null) {
                List<Passenger> passengers = Arrays.asList(mapper.readValue(stream, Passenger[].class));
                passengerRepository.saveAll(passengers);
            }
        }
    }

}
