package com.keyin.airportapi.airport.tests;

import com.keyin.airportapi.airport.Airport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportTest {

    @Test
    void testAirportConstructorAndGetters() {
        Airport airport = new Airport(1, "ABC", "416");
        assertEquals(1, airport.getAirportId());
        assertEquals("ABC", airport.getAirportName());
        assertEquals("416", airport.getAreaCode());
    }

    @Test
    void testSetters() {
        Airport airport = new Airport(2, "XYZ", "123");
        airport.setAirportId(5);
        airport.setAirportName("YVR");
        airport.setAreaCode("605");

        assertEquals(5, airport.getAirportId());
        assertEquals("YVR", airport.getAirportName());
        assertEquals("605", airport.getAreaCode());
    }

    @Test
    void testToString() {
        Airport airport = new Airport(2, "Airport", "777");
        String expected = "2 Airport 777";
        assertEquals(expected, airport.toString());
    }
}
