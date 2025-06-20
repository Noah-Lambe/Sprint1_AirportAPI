package com.keyin.airportapi.passenger;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    // relationship (e.g. a Passenger lives in one city)
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    // many-to-many relationship with aircraft
    @ManyToMany
    @JoinTable(
            name = "passenger_aircraft",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_id")
    )
    private Set<Aircraft> aircraft = new HashSet<>();

    // Getters and Setters
}
