package com.keyin.airportapi.airline;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.flight.Flight;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long airlineId;

    private String airlineName;

    @OneToMany(mappedBy = "airline", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Aircraft> aircraftList;

    @OneToMany(mappedBy = "airline")
    @JsonIgnore
    private List<Flight> flights;

    public Airline() {}

    public Airline(Long airlineId, String airlineName) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
