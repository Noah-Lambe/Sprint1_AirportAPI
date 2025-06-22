package com.keyin.airportapi.airport.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.airport.AirportService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AirportRepositoryTest {

    @Autowired
    private AirportRepository airportRepository;

    private Airport airport;

    @BeforeEach
    public void setUp() {
        airport = new Airport();
        airport.setAirportId(1);
        airport.setAirportName("Test International Airport");
        airport.setAreaCode("TIA");
    }

    @Test
    public void testSaveAirport() {
        Airport savedAirport = airportRepository.save(airport);
        assertThat(savedAirport).isNotNull();
        assertThat(savedAirport.getAirportName()).isEqualTo("Test International Airport");
    }

    @Test
    public void testFindById() {
        Airport savedAirport = airportRepository.save(airport);
        Optional<Airport> retrieved = airportRepository.findById(savedAirport.getAirportId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getAreaCode()).isEqualTo("TIA");
    }

    @Test
    public void testDeleteAirport() {
        Airport savedAirport = airportRepository.save(airport);
        airportRepository.deleteById(savedAirport.getAirportId());
        Optional<Airport> result = airportRepository.findById(savedAirport.getAirportId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void testFindAllAirports() {
        airportRepository.save(airport);

        Airport airport2 = new Airport();
        airport2.setAirportId(2);
        airport2.setAirportName("Second Airport");
        airport2.setAreaCode("SAP");

        airportRepository.save(airport2);

        Iterable<Airport> airports = airportRepository.findAll();
        assertThat(airports).hasSizeGreaterThanOrEqualTo(2);
    }
}
