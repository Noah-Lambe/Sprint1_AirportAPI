package com.keyin.airportapi.loaderTest;

import com.keyin.airportapi.aircraft.AircraftRepository;
import com.keyin.airportapi.airline.AirlineRepository;
import com.keyin.airportapi.airport.AirportRepository;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.flight.FlightRepository;
import com.keyin.airportapi.gate.GateRepository;
import com.keyin.airportapi.loader.DataLoader;
import com.keyin.airportapi.passenger.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataLoaderTest {

    private AircraftRepository aircraftRepository = Mockito.mock(AircraftRepository.class);
    private AirlineRepository airlineRepository = Mockito.mock(AirlineRepository.class);
    private AirportRepository airportRepository = Mockito.mock(AirportRepository.class);
    private CityRepository cityRepository = Mockito.mock(CityRepository.class);
    private FlightRepository flightRepository = Mockito.mock(FlightRepository.class);
    private GateRepository gateRepository = Mockito.mock(GateRepository.class);
    private PassengerRepository passengerRepository = Mockito.mock(PassengerRepository.class);

    private DataLoader dataLoader;

    @BeforeEach
    public void setUp() {
        dataLoader = new DataLoader(
                aircraftRepository,
                airlineRepository,
                airportRepository,
                cityRepository,
                flightRepository,
                gateRepository,
                passengerRepository
        );
    }

    @Test
    public void testLoadAircraftsWhenEmpty() throws Exception {
        when(aircraftRepository.count()).thenReturn(0L);

        String json = "[{\"type\":\"TestJet\",\"numberOfPassengers\":100,\"airline\":{\"airlineId\":1,\"airlineName\":\"TestAir\"}}]";
        ByteArrayInputStream stream = new ByteArrayInputStream(json.getBytes());

        // Subclass with overridden stream loader
        DataLoader testLoader = new DataLoader(
                aircraftRepository,
                airlineRepository,
                airportRepository,
                cityRepository,
                flightRepository,
                gateRepository,
                passengerRepository
        ) {
            @Override
            public InputStream getResourceAsStream(String path) {
                if ("/aircraft.json".equals(path)) {
                    return stream;
                }
                return null;
            }
        };

        testLoader.run();

        verify(aircraftRepository, times(1)).saveAll(anyList());
    }


    @Test
    public void testDoesNotLoadAircraftWhenNotEmpty() throws Exception {
        when(aircraftRepository.count()).thenReturn(5L);

        dataLoader.run();

        verify(aircraftRepository, never()).saveAll(anyList());
    }

    @Test
    public void testLoadAllWhenEmpty() throws Exception {

        when(aircraftRepository.count()).thenReturn(0L);
        when(airlineRepository.count()).thenReturn(0L);
        when(airportRepository.count()).thenReturn(0L);
        when(cityRepository.count()).thenReturn(0L);
        when(flightRepository.count()).thenReturn(0L);
        when(gateRepository.count()).thenReturn(0L);
        when(passengerRepository.count()).thenReturn(0L);
        
        String dummyJson = "[]";

        DataLoader spyLoader = Mockito.spy(dataLoader);
        
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/aircraft.json");
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/airline.json");
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/airport.json");
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/city.json");
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/flight.json");
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/gate.json");
        doReturn(new ByteArrayInputStream(dummyJson.getBytes())).when(spyLoader).getResourceAsStream("/passenger.json");

        spyLoader.run();

        verify(aircraftRepository, times(1)).saveAll(anyList());
        verify(airlineRepository, times(1)).saveAll(anyList());
        verify(airportRepository, times(1)).saveAll(anyList());
        verify(cityRepository, times(1)).saveAll(anyList());
        verify(flightRepository, times(1)).saveAll(anyList());
        verify(gateRepository, times(1)).saveAll(anyList());
        verify(passengerRepository, times(1)).saveAll(anyList());
    }
}
