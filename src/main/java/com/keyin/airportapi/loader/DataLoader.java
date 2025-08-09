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
    public void run(String... args)  throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream aircraftStream = getClass().getResourceAsStream("/aircraft.json");
        List<Aircraft> aircraft = Arrays.asList(mapper.readValue(aircraftStream, Aircraft[].class));
        aircraftRepository.saveAll(aircraft);

        InputStream airlineStream = getClass().getResourceAsStream("/airline.json");
        List<Airline> airlines = Arrays.asList(mapper.readValue(airlineStream, Airline[].class));
        airlineRepository.saveAll(airlines);

        InputStream airportStream = getClass().getResourceAsStream("/airport.json");
        List<Airport> airports = Arrays.asList(mapper.readValue(airportStream, Airport[].class));
        airportRepository.saveAll(airports);

        InputStream cityStream = getClass().getResourceAsStream("/city.json");
        List<City> cities = Arrays.asList(mapper.readValue(cityStream, City[].class));
        cityRepository.saveAll(cities);

        InputStream flightStream = getClass().getResourceAsStream("/flight.json");
        List<Flight> flights = Arrays.asList(mapper.readValue(flightStream, Flight[].class));
        flightRepository.saveAll(flights);

        InputStream gateStream = getClass().getResourceAsStream("/gate.json");
        List<Gate> gates = Arrays.asList(mapper.readValue(gateStream, Gate[].class));
        gateRepository.saveAll(gates);

        InputStream passengerStream = getClass().getResourceAsStream("/passenger.json");
        List<Passenger> passengers = Arrays.asList(mapper.readValue(passengerStream, Passenger[].class));
        passengerRepository.saveAll(passengers);
    }
}
