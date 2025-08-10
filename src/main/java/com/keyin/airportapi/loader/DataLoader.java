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

    private final ObjectMapper mapper;
    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final FlightRepository flightRepository;
    private final GateRepository gateRepository;
    private final PassengerRepository passengerRepository;

    public DataLoader(ObjectMapper mapper,
                      AircraftRepository aircraftRepository,
                      AirlineRepository airlineRepository,
                      AirportRepository airportRepository,
                      CityRepository cityRepository,
                      FlightRepository flightRepository,
                      GateRepository gateRepository,
                      PassengerRepository passengerRepository) {
        this.mapper = mapper;
        this.aircraftRepository = aircraftRepository;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
        this.flightRepository = flightRepository;
        this.gateRepository = gateRepository;
        this.passengerRepository = passengerRepository;
    }

    // Helper method
    public InputStream getResourceAsStream(String path) {
        return getClass().getResourceAsStream(path);
    }

    @Override
    public void run(String... args) throws Exception {

        if (cityRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/city.json");
            if (stream == null) {
                System.out.println("city.json NOT found!");
            } else {
                System.out.println("Loading city.json...");
                List<City> cities = Arrays.asList(mapper.readValue(stream, City[].class));
                cityRepository.saveAll(cities);
                cityRepository.flush();
                System.out.println("Saved " + cities.size() + " cities.");
            }
        } else {
            System.out.println("City data already present, skipping load.");
        }

        if (airportRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/airport.json");
            if (stream == null) {
                System.out.println("airport.json NOT found!");
            } else {
                System.out.println("Loading airport.json...");
                List<Airport> airports = Arrays.asList(mapper.readValue(stream, Airport[].class));
                airportRepository.saveAll(airports);
                airportRepository.flush();
                System.out.println("Saved " + airports.size() + " airports.");
            }
        } else {
            System.out.println("Airport data already present, skipping load.");
        }

        if (airlineRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/airline.json");
            if (stream == null) {
                System.out.println("airline.json NOT found!");
            } else {
                System.out.println("Loading airline.json...");
                List<Airline> airlines = Arrays.asList(mapper.readValue(stream, Airline[].class));
                airlineRepository.saveAll(airlines);
                airlineRepository.flush();
                System.out.println("Saved " + airlines.size() + " airlines.");
            }
        } else {
            System.out.println("Airline data already present, skipping load.");
        }

        if (aircraftRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/aircraft.json");
            if (stream == null) {
                System.out.println("aircraft.json NOT found!");
            } else {
                System.out.println("Loading aircraft.json...");
                List<Aircraft> aircraft = Arrays.asList(mapper.readValue(stream, Aircraft[].class));
                aircraftRepository.saveAll(aircraft);
                aircraftRepository.flush();
                System.out.println("Saved " + aircraft.size() + " aircraft.");
            }
        } else {
            System.out.println("Aircraft data already present, skipping load.");
        }

        if (gateRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/gate.json");
            if (stream == null) {
                System.out.println("gate.json NOT found!");
            } else {
                System.out.println("Loading gate.json...");
                List<Gate> gates = Arrays.asList(mapper.readValue(stream, Gate[].class));
                gateRepository.saveAll(gates);
                gateRepository.flush();
                System.out.println("Saved " + gates.size() + " gates.");
            }
        } else {
            System.out.println("Gate data already present, skipping load.");
        }

        if (passengerRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/passenger.json");
            if (stream == null) {
                System.out.println("passenger.json NOT found!");
            } else {
                System.out.println("Loading passenger.json...");
                List<Passenger> passengers = Arrays.asList(mapper.readValue(stream, Passenger[].class));
                passengerRepository.saveAll(passengers);
                passengerRepository.flush();
                System.out.println("Saved " + passengers.size() + " passengers.");
            }
        } else {
            System.out.println("Passenger data already present, skipping load.");
        }

        if (flightRepository.count() == 0) {
            InputStream stream = getResourceAsStream("/data/flight.json");
            if (stream == null) {
                System.out.println("flight.json NOT found!");
            } else {
                System.out.println("Loading flight.json...");
                List<Flight> flights = Arrays.asList(mapper.readValue(stream, Flight[].class));
                flightRepository.saveAll(flights);
                flightRepository.flush();
                System.out.println("Saved " + flights.size() + " flights.");
            }
        } else {
            System.out.println("Flight data already present, skipping load.");
        }

    }
}
