package com.keyin.airportapi.passenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keyin.airportapi.flight.Flight;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import com.keyin.airportapi.city.City;
import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.airport.Airport;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    // relationship (e.g. a Passenger lives in one city)
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany
    @JoinTable(
            name = "passenger_aircraft",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_id")
    )
    private Set<Aircraft> aircraft = new HashSet<>();

    @ManyToMany(mappedBy = "passengers")
    @JsonIgnore
    private List<Flight> flights;

    public Passenger() {}

    public Passenger(Long id, String firstName, String lastName, String phoneNumber, City city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Aircraft> getAircraft() {
        return aircraft;
    }

    public void setAircraft(Set<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }

    @JsonIgnore
    public Aircraft[] getAircraftList() {
        if (aircraft == null || aircraft.isEmpty()) {
            return new Aircraft[0];
        }
        return aircraft.toArray(new Aircraft[0]);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
        flight.getPassengers().add(this); // synchronize the relationship
    }
}