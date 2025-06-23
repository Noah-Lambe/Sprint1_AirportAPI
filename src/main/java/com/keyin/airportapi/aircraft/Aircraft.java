package com.keyin.airportapi.aircraft;

import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;

    private String type;
    private String airlineName;
    private int numberOfPassengers;

    @ManyToMany(mappedBy = "aircraftFlown")
    private List<Passenger> passengers;

    @ManyToMany
    @JoinTable(
            name = "aircraft_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    private List<Airport> airports;

    public Aircraft() {}

    public Aircraft(String type, String airlineName, int numberOfPassengers) {
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public String getType() {
        return type;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = type;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
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
}
