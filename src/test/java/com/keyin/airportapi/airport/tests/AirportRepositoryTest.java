package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.city.City;
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

    @Autowired
    private CityRepository cityRepository;

    private Airport airport;

    @BeforeEach
    public void setUp() {

        City testCity = new City();
        testCity.setName("Testville");
        testCity.setState("Testland");
        testCity.setPopulation(500000);
        testCity = cityRepository.save(testCity);

        airport = new Airport();
        airport.setAirportName("Test International Airport");
        airport.setAreaCode("TIA");
        airport.setCity(testCity);
    }

    @Test
    public void testSaveAirport() {
        Airport savedAirport = airportRepository.save(airport);
        assertThat(savedAirport).isNotNull();
        assertThat(savedAirport.getAirportName()).isEqualTo("Test International Airport");
        assertThat(savedAirport.getCity()).isNotNull();
        assertThat(savedAirport.getCity().getName()).isEqualTo("Testville");
    }

    @Test
    public void testFindById() {
        Airport savedAirport = airportRepository.save(airport);
        Optional<Airport> retrieved = airportRepository.findById(savedAirport.getAirportId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getAreaCode()).isEqualTo("TIA");
        assertThat(retrieved.get().getCity().getName()).isEqualTo("Testville");
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

        City secondCity = new City();
        secondCity.setName("Second City");
        secondCity.setState("ProvinceX");
        secondCity.setPopulation(300000);
        secondCity = cityRepository.save(secondCity);

        Airport airport2 = new Airport();
        airport2.setAirportName("Second Airport");
        airport2.setAreaCode("SAP");
        airport2.setCity(secondCity);

        airportRepository.save(airport2);

        Iterable<Airport> airports = airportRepository.findAll();
        assertThat(airports).hasSizeGreaterThanOrEqualTo(2);
    }
}
