package com.keyin.airportapi.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keyin.airportapi.aircraft.Aircraft;
import com.keyin.airportapi.airline.Airline;
import com.keyin.airportapi.airport.Airport;
import com.keyin.airportapi.gate.Gate;
import com.keyin.airportapi.passenger.Passenger;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long flightId;
    private String flightNumber;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;

    @ManyToOne
    private Airport originAirport;

    @ManyToOne
    private Airport destinationAirport;

    @ManyToOne
    private Aircraft aircraft;

    @ManyToOne
    private Gate gate;

    @ManyToOne
    private Airline airline;

    @ManyToMany
    @JoinTable(
            name = "flight_passengers",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    private List<Passenger> passengers;


    public Flight() {}

    public Flight(
                  String flightNumber,
                  Airline airline,
                  String status,
                  LocalDateTime departureTime,
                  LocalDateTime arrivalTime,
                  Airport originAirport,
                  Airport destinationAirport,
                  Gate gate,
                  Aircraft aircraft
    )
    {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.status = status;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.gate = gate;
        this.aircraft = aircraft;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
        passenger.getFlights().add(this);
    }
}
