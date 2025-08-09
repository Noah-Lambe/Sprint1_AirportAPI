package com.keyin.airportapi.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.aircraft.AircraftRepository;
import com.keyin.airportapi.airline.Airline;
import com.keyin.airportapi.airline.AirlineRepository;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.flight.FlightRepository;
import com.keyin.airportapi.gate.GateRepository;
import com.keyin.airportapi.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        InputStream aircraftStream = getClass().getResourceAsStream("/data.json");
        List<Aircraft> aircrafts = Arrays.asList(mapper.readValue(aircraftStream, Aircraft[].class));
        aircraftRepository.saveAll(aircrafts);

        InputStream airlineStream = getClass().getResourceAsStream("/data.json");
        List<Airline> airlines = Arrays.asList(mapper.readValue(airlineStream, Airline[].class));
        airlineRepository.saveAll(airlines);


    }


}
