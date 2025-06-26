package com.keyin.airportapi.city;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.passenger.Passenger;

import java.util.List;

@Entity
public class City {
    @Id
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "city_sequence")

    private long id;
    private String name;
    private String state;
    private long population;

    @OneToMany(mappedBy = "city")
    @JsonIgnoreProperties("city")
    private List<Airport> airports;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<Passenger> passengers;

    public City() {}

    public City(long id, String name, String state, long population) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.population = population;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
