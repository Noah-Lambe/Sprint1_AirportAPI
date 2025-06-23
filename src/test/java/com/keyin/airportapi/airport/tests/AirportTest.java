package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.city.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportTest {

    @Test
    void testAirportConstructorAndGetters() {
        City city = new City(1, "Mount Pearl", "Newfoundland");
        Airport airport = new Airport(1, "ABC", "416", city);

        assertEquals(1, airport.getAirportId());
        assertEquals("ABC", airport.getAirportName());
        assertEquals("416", airport.getAreaCode());
        assertEquals(city, airport.getCity());
    }

    @Test
    void testSetters() {
        City city = new City(2, "Vancouver", "British Columbia");
        Airport airport = new Airport(2, "XYZ", "123", city);

        airport.setAirportId(5);
        airport.setAirportName("YVR");
        airport.setAreaCode("605");

        City newCity = new City(3, "Toronto", "Ontario");
        airport.setCity(newCity);

        assertEquals(5, airport.getAirportId());
        assertEquals("YVR", airport.getAirportName());
        assertEquals("605", airport.getAreaCode());
        assertEquals(newCity, airport.getCity());
    }

    @Test
    void testToString() {
        City city = new City(4, "Halifax", "Nova Scotia");
        Airport airport = new Airport(2, "Airport", "777", city);

        String expected = "2 Airport 777"; // Adjust if toString includes city
        assertEquals(expected, airport.toString());
    }
}
