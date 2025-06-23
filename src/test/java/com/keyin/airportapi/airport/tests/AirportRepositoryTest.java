package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AirportRepositoryTest {

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private CityRepository cityRepository;

    private Airport airport;
    private City testCity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testCity = new City();
        testCity.setName("Testville");
        testCity.setState("Testland");
        testCity.setPopulation(500000);

        airport = new Airport();
        airport.setAirportName("Test International Airport");
        airport.setAreaCode("TIA");
        airport.setCity(testCity);
    }

    @Test
    public void testSaveAirport() {
        when(cityRepository.save(testCity)).thenReturn(testCity);
        when(airportRepository.save(airport)).thenReturn(airport);

        City savedCity = cityRepository.save(testCity);
        Airport savedAirport = airportRepository.save(airport);

        assertThat(savedCity).isNotNull();
        assertThat(savedCity.getName()).isEqualTo("Testville");

        assertThat(savedAirport).isNotNull();
        assertThat(savedAirport.getAirportName()).isEqualTo("Test International Airport");
        assertThat(savedAirport.getCity().getName()).isEqualTo("Testville");

        verify(cityRepository).save(testCity);
        verify(airportRepository).save(airport);
    }

    @Test
    public void testFindById() {
        airport.setAirportId(1L);
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        Optional<Airport> retrieved = airportRepository.findById(1L);

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getAreaCode()).isEqualTo("TIA");
        assertThat(retrieved.get().getCity().getName()).isEqualTo("Testville");

        verify(airportRepository).findById(1L);
    }

    @Test
    public void testDeleteAirport() {
        doNothing().when(airportRepository).deleteById(1L);

        airportRepository.deleteById(1L);

        verify(airportRepository).deleteById(1L);
    }

    @Test
    public void testFindAllAirports() {
        City secondCity = new City();
        secondCity.setName("Second City");
        secondCity.setState("ProvinceX");
        secondCity.setPopulation(300000);

        Airport airport2 = new Airport();
        airport2.setAirportName("Second Airport");
        airport2.setAreaCode("SAP");
        airport2.setCity(secondCity);

        List<Airport> airportList = Arrays.asList(airport, airport2);
        when(airportRepository.findAll()).thenReturn(airportList);

        List<Airport> airports = (List<Airport>) airportRepository.findAll();

        assertThat(airports).hasSizeGreaterThanOrEqualTo(2);

        verify(airportRepository).findAll();
    }
}
