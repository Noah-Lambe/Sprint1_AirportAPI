package com.keyin.airportapi.aircraft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.keyin.airportapi.airline.Airline;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;
import jakarta.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;

    private String type;
    private String airlineName;
    private int numberOfPassengers;

    @ManyToMany(mappedBy = "aircraft")
    @JsonIgnore
    private List<Passenger> passengers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "aircraft_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    private List<Airport> airports;

    @ManyToOne
    @JoinColumn(name="airline_id", nullable=false)
    private Airline airline;

    public Aircraft() {}

    public Aircraft(String type, Airline airline, int numberOfPassengers) {
        this.type = type;
        this.airline = airline;
        this.numberOfPassengers = numberOfPassengers;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
