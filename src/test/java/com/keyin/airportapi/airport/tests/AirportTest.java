package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.city.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportTest {

    @Test
    void testAirportConstructorAndGetters() {
        City city = new City(1L, "Mount Pearl", "Newfoundland", 100000L);
        Airport airport = new Airport(1L, "ABC", "416", city);

        assertEquals(1, airport.getAirportId());
        assertEquals("ABC", airport.getAirportName());
        assertEquals("416", airport.getAreaCode());
        assertEquals(city, airport.getCity());
    }

    @Test
    void testSetters() {
        City city = new City(2L, "Vancouver", "British Columbia", 2500000L);
        Airport airport = new Airport(2L, "XYZ", "123", city);

        airport.setAirportId(5L);
        airport.setAirportName("YVR");
        airport.setAreaCode("605");

        City newCity = new City(3L, "Toronto", "Ontario", 3000000L);
        airport.setCity(newCity);

        assertEquals(5L, airport.getAirportId());
        assertEquals("YVR", airport.getAirportName());
        assertEquals("605", airport.getAreaCode());
        assertEquals(newCity, airport.getCity());
    }

    @Test
    void testToString() {
        City city = new City(4L, "Halifax", "Nova Scotia", 500000L);
        Airport airport = new Airport(2L, "Airport", "777", city);

        String expected = "ID: 2 Name: Airport Area Code: 777 City: Halifax"; // Adjust if toString includes city
        assertEquals(expected, airport.toString());
    }
}
